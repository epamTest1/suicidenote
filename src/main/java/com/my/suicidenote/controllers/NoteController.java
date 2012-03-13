package com.my.suicidenote.controllers;

import com.my.suicidenote.common.Parameters;
import com.my.suicidenote.dto.Note;
import com.my.suicidenote.dto.Session;
import com.my.suicidenote.exception.SpamException;
import com.my.suicidenote.repo.NoteRepository;
import com.my.suicidenote.repo.SessionRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Oleksandr_Shcherbyna
 */
@Controller
public class NoteController {

    public static final int MINIMAL_INTERVAL_IN_MINUTES = -1;
    public static final int SESSION_LIFETIME_IN_HOUR = -1;
    
    public static final int HTTP_STATUS_NON_AUTHORITATIVE_INFORMATION  = 203;
    public static final int HTTP_STATUS_ALREADY_REPORTED = 208;
    
    @Autowired
    NoteRepository repository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    private ReCaptcha reCaptcha;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");

    private String stripHTMLTag(String parameter) {
        return parameter != null ? parameter.replaceAll("\\<\\/?([^\\>]*)\\>", "") : "";
    }

    /**
     * Delete all session older than time interval you select.
     */
    private void clearSessions() {
        Calendar checkTime = Calendar.getInstance();
        checkTime.add(Calendar.HOUR, SESSION_LIFETIME_IN_HOUR);
        sessionRepository.delete(sessionRepository.findByTimestampLessThan(checkTime.getTimeInMillis()));
    }

    /**
     * If user already send a note during previous minute then show them a
     * captcha.
     */
    private boolean isPossibleSpam(String ip) {
        Calendar checkTime = Calendar.getInstance();
        checkTime.add(Calendar.MINUTE, MINIMAL_INTERVAL_IN_MINUTES);
        List<Session> lastSessions = sessionRepository.findByIpAndTimestampGreaterThan(ip, checkTime.getTimeInMillis());
        return lastSessions.size() > 0;
    }

    @RequestMapping(value = "/note")
    public void saveNote(HttpServletRequest request, HttpServletResponse response) throws SpamException {
    	String reCaptchaChallenge = request.getParameter("recaptcha_challenge_field");
    	String reCaptchaResponse = request.getParameter("recaptcha_response_field");
    	if (reCaptchaChallenge != null) {
            ReCaptchaResponse recaptchaChecker = reCaptcha.checkAnswer(request.getRemoteAddr(), reCaptchaChallenge, reCaptchaResponse);
            if (!recaptchaChecker.isValid()) {
            	response.setStatus(HTTP_STATUS_NON_AUTHORITATIVE_INFORMATION);     
            	return;
            }
    	}
    	else if (isPossibleSpam(request.getRemoteAddr())) {
    		response.setStatus(HTTP_STATUS_ALREADY_REPORTED);
        	return;
            //throw new SpamException();
        }

        Note note = new Note();
        note.setFrom(stripHTMLTag(request.getParameter(Parameters.FROM)));
        note.setSay(stripHTMLTag(request.getParameter(Parameters.SAY)));
        // recipient can be more then one
        StringBuilder sendTo = new StringBuilder();
        sendTo.append(stripHTMLTag(request.getParameter(Parameters.SEND_TO))).append(",");
        int i = 0x0;
        while (request.getParameter(Parameters.SEND_TO + "-" + i) != null) {
            sendTo.append(stripHTMLTag(request.getParameter(Parameters.SEND_TO + "-" + i))).append(",");
            i++;
        }
        note.setSentTo(sendTo.toString());
        note.setTo(stripHTMLTag(request.getParameter(Parameters.TO)));
        
        String timeZone = stripHTMLTag(request.getParameter(Parameters.TIME_ZONE));
		timeZone = timeZone.startsWith("-") ? "GMT".concat(timeZone) : "GMT+".concat(timeZone);
		note.setTimeZone(timeZone);
		
        Calendar currentUserDate = Calendar.getInstance(TimeZone.getTimeZone(note.getTimeZone()));
        try {
            currentUserDate.setTime(sdf.parse(stripHTMLTag(request.getParameter(Parameters.WHEN))));
		}
		catch (ParseException ex) {
            Logger.getLogger(NoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar currentHostDate = Calendar.getInstance();
        currentHostDate.setTimeInMillis(currentUserDate.getTimeInMillis());
        note.setWhen(currentUserDate.getTimeInMillis());
        repository.save(note);

        clearSessions();
        Session session = new Session();
        session.setIp(request.getRemoteAddr());
        session.setTimestamp(Calendar.getInstance().getTimeInMillis());
        sessionRepository.save(session);
    }

    @RequestMapping(value = "/noterecaptcha", method = GET)
    public @ResponseBody
    String generateRecaptcha() {
		//return "<form action=\"/noterecaptcha\" method=\"post\">" + reCaptcha.createRecaptchaHtml("Error message", null) + "</form>";
		return reCaptcha.createRecaptchaHtml("Stay calm. Try again.", null);
    }

    @RequestMapping(value = "/noterecaptcha", method = POST)
	public @ResponseBody String checkRecaptcha(HttpServletRequest request, @RequestParam("recaptcha_challenge_field") final String reCaptchaChallenge,
            @RequestParam("recaptcha_response_field") final String reCaptchaResponse) {
        ReCaptchaResponse response = reCaptcha.checkAnswer(request.getRemoteAddr(), reCaptchaChallenge, reCaptchaResponse);
        if (!response.isValid()) {
			return "{\"recaptcha\":\"fail\"}";
        }
		return "{\"recaptcha\":\"success\"}";
    }
	// @RequestMapping(value="/notestosend")
	// public @ResponseBody List<Note> getNotes() {
	// Calendar currentDate = Calendar.getInstance();
	// return repository.findByWhenLessThan(currentDate.getTimeInMillis());
	// }
}
