package com.my.suicidenote.mail;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.my.suicidenote.common.Parameters;
import com.my.suicidenote.db.MongoDB;
import it.sauronsoftware.cron4j.Scheduler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrii_Manuiev
 */
public class Postman {

    private static final String collection = "notes";
    private Scheduler s = new Scheduler();
    
    private List<Letter> prepareData() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        
        DBCollection notes = MongoDB.findCollection(collection);
                
        List<Letter> msgList = new ArrayList<Letter>();
        
        if (notes != null) {
            DBCursor cur = notes.find();
            
            while (cur.hasNext()) {
                try {
                    DBObject advObj = cur.next();
                    DBObject detailObj = (DBObject) advObj.get(Parameters.DETAIL);
                    Letter letter = new Letter();
                    
                    letter.setTo(detailObj.get(Parameters.TO).toString());
                    letter.setWhen(detailObj.get(Parameters.WHEN).toString());
                    letter.setSendTo(detailObj.get(Parameters.SEND_TO).toString()); 
                    letter.setFrom(detailObj.get(Parameters.FROM).toString());
                    letter.setBody(detailObj.get(Parameters.SAY).toString());
                        
                    Calendar senderTime = Calendar.getInstance();
                    senderTime.setTime(sdf.parse(letter.getWhen()));
                    
                    if (Calendar.getInstance().compareTo(senderTime) >= 0) {
                        msgList.add(letter);
                        //udate object
                        detailObj.put(Parameters.SENT, true);
                        DBObject updObj = advObj;
                        updObj.put(Parameters.SENT, detailObj);
                        notes.update(advObj, updObj);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Postman.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cur.close();
        }
        return msgList;
    }
            
    public void init() {
        s.schedule("* * * * *", new Runnable() {
        //s.schedule("*/5 * * * *", new Runnable() {
            public void run() {
                List<Letter> list = prepareData();
                for (Letter letter : list) {
                    new SendMail(letter).send();
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
