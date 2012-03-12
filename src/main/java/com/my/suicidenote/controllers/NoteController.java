package com.my.suicidenote.controllers;

import com.my.suicidenote.common.Parameters;
import com.my.suicidenote.dto.Note;
import com.my.suicidenote.repo.NoteRepository;
import com.my.suicidenote.repo.SessionRepository;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 
 * @author Oleksandr_Shcherbyna
 */
@Controller
public class NoteController {

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

	@RequestMapping(value = "/note")
	@ResponseStatus(HttpStatus.OK)
	public void saveNote(HttpServletRequest request) throws IOException {

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
