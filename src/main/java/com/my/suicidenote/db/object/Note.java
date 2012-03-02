package com.my.suicidenote.db.object;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.Calendar;

/**
 *
 * @author Andrii_Manuiev
 */
public class Note {
    
    static public enum DB_FIELD_NAME {
        _id(), to(),say(), when(), from(), sendTo(), timeZone();
    }

    private String id;
    private String to;
    private String say;
    private long when;
    private String from;
    private String sentTo;
    private String timeZone;

    public String getId() {
        return id;
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Calendar getWhen() {
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(when);
        return result;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public BasicDBObject toDbObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put(DB_FIELD_NAME.to.name(), to);
        document.put(DB_FIELD_NAME.say.name(), say);
        document.put(DB_FIELD_NAME.when.name(), when);
        document.put(DB_FIELD_NAME.from.name(), from);
        document.put(DB_FIELD_NAME.sendTo.name(), sentTo);
        document.put(DB_FIELD_NAME.timeZone.name(), timeZone);
        
        return document;
    }
    
    public static Note fromDBObject(DBObject document) {
        Note note = new Note();
        note.id = document.get(DB_FIELD_NAME._id.name()).toString();
        note.to = (String) document.get(DB_FIELD_NAME.to.name());
        note.say = (String) document.get(DB_FIELD_NAME.say.name());
        note.when = (Long) document.get(DB_FIELD_NAME.when.name());
        note.from = (String) document.get(DB_FIELD_NAME.from.name());
        note.sentTo = (String) document.get(DB_FIELD_NAME.sendTo.name());
        note.timeZone = (String) document.get(DB_FIELD_NAME.timeZone.name());
        
        return note;
    }
}