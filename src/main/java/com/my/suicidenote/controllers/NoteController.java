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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        } catch (ParseException ex) {
            Logger.getLogger(NoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar currentHostDate = Calendar.getInstance();
        currentHostDate.setTimeInMillis(currentUserDate.getTimeInMillis());

        note.setWhen(currentUserDate.getTimeInMillis());
        repository.save(note);
    }
//    @RequestMapping(value="/notestosend")
//    public @ResponseBody List<Note> getNotes() {
//        Calendar currentDate = Calendar.getInstance();              
//        return repository.findByWhenLessThan(currentDate.getTimeInMillis());
//    }
}