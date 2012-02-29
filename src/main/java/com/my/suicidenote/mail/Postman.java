package com.my.suicidenote.mail;

import com.mongodb.BasicDBObject;
import com.my.suicidenote.db.NoteHelper;
import com.my.suicidenote.db.object.Note;
import it.sauronsoftware.cron4j.Scheduler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrii_Manuiev
 */
public class Postman {

    private Scheduler s = new Scheduler();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    private static final String CRON_EXPRESSION = "*/5 * * * *";
            
    private List<Note> prepareData() {
        
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("sent", false);
        
        List<Note> result = new ArrayList<Note>(); 
        List<Note> notes = NoteHelper.getNotes(searchQuery);
        Calendar senderTime = Calendar.getInstance();
        
        try {
            for(Note note : notes) {
                senderTime.setTime(sdf.parse(note.getWhen()));
                if (Calendar.getInstance().compareTo(senderTime) >= 0) {
                    result.add(note);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Postman.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
            
    public void init() {
        s.schedule(CRON_EXPRESSION, new Runnable() {
            @Override
            public void run() {
                List<Note> notes = prepareData();
                for (Note note : notes) {
                    SendMail.send(note);
                }
            }
        });
    }
    
    public void start() {
        s.start();
    }
    
    public void stop() {
        s.stop();
    }
}
