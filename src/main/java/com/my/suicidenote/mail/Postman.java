package com.my.suicidenote.mail;

import com.mongodb.BasicDBObject;
import com.my.suicidenote.db.NoteHelper;
import com.my.suicidenote.db.object.Note;
import it.sauronsoftware.cron4j.Scheduler;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Andrii_Manuiev
 */
public class Postman {

    private Scheduler s = new Scheduler();
    private static final String CRON_EXPRESSION = "*/5 * * * *";
            
    private List<Note> prepareData() {
        Calendar currentDate = Calendar.getInstance();
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append(Note.DB_FIELD_NAME.when.name(), new BasicDBObject("$lte", currentDate.getTimeInMillis()));
        return NoteHelper.getNotes(searchQuery);
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