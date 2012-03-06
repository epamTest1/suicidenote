package com.my.suicidenote.mail;

import com.my.suicidenote.dto.Note;
import com.my.suicidenote.repo.NoteRepository;

import it.sauronsoftware.cron4j.Scheduler;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andrii_Manuiev
 */
public class Postman {

	@Autowired
	NoteRepository repository;
	
	@Autowired
	Mailer mailer;
	
    private Scheduler s = new Scheduler();
    private static final String CRON_EXPRESSION = "*/5 * * * *";
            
    private List<Note> prepareData() {
        Calendar currentDate = Calendar.getInstance();              
        return repository.findByWhenLessThan(currentDate.getTimeInMillis());
    }
            
    public void init() {
        s.schedule(CRON_EXPRESSION, new Runnable() {
            @Override
            public void run() {
                List<Note> notes = prepareData();
                for (Note note : notes) {
                    mailer.send(note);
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