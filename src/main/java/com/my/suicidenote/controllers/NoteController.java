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

    public static final int MINIMAL_INTERVAL = -1;
    
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
     * If user already send a note during previous minute then show them a captcha.
     */
    private boolean isPossibleSpam(String ip) {
        Calendar checkTime = Calendar.getInstance();
        checkTime.add(Calendar.MINUTE, MINIMAL_INTERVAL);
        List<Session> lastSessions = sessionRepository.findByIpAndTimestampGreaterThan(ip, checkTime.getTimeInMillis());
        return lastSessions.size() > 0;
    }

    @ExceptionHandler(SpamException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public void possibleSpamException() {
    }
    
    @RequestMapping(value = "/note")
    @ResponseStatus(HttpStatus.OK)
    public void saveNote(HttpServletRequest request) throws SpamException {

        if (isPossibleSpam(request.getRemoteAddr())) {
            throw new SpamException();
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
        } catch (ParseException ex) {
            Logger.getLogger(NoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar currentHostDate = Calendar.getInstance();
        currentHostDate.setTimeInMillis(currentUserDate.getTimeInMillis());

        note.setWhen(currentUserDate.getTimeInMillis());
        repository.save(note);
        
        Session session = new Session();
        session.setIp(request.getRemoteAddr());
        session.setTimestamp(Calendar.getInstance().getTimeInMillis());
        sessionRepository.save(session);
    }
    
    @RequestMapping(value="/noterecaptcha", method=GET)  
    public @ResponseBody String generateRecaptcha(){  
     return "<form action=\"/noterecaptcha\" method=\"post\">"+reCaptcha.createRecaptchaHtml("Error message", null) + "</form>";  
    }      

    @RequestMapping(value="/noterecaptcha", method=POST)  
    public String checkRecaptcha(HttpServletRequest request, 
    		   @RequestParam("recaptcha_challenge_field") final String reCaptchaChallenge,  
    		   @RequestParam("recaptcha_response_field") final String reCaptchaResponse){  
    	  ReCaptchaResponse response = reCaptcha.checkAnswer(request.getRemoteAddr(), reCaptchaChallenge, reCaptchaResponse);  
    	  if(!response.isValid()){
    		  System.out.println("Recaptcha is invalid");
    		  return "redirect:/noterecaptcha";
    	  }    
    	  return "forward:/";
    }
    
//    @RequestMapping(value="/notestosend")
//    public @ResponseBody List<Note> getNotes() {
//        Calendar currentDate = Calendar.getInstance();              
//        return repository.findByWhenLessThan(currentDate.getTimeInMillis());
//    }
}
