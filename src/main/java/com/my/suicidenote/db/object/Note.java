package com.my.suicidenote.db.object;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author Andrii_Manuiev
 */
public class Note {
    
    private String id;
    private String to;
    private String say;
    private String when;
    private String from;
    private boolean sent;
    private String sentTo;

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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
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

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }
    
    public BasicDBObject toDbObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put("to", to);
        document.put("say", say);
        document.put("when", when);
        document.put("from", from);
        document.put("sent", sent);
        document.put("sentTo", sentTo);
        
        return document;
    }
    
    public static Note fromDBObject(DBObject document) {
        Note note = new Note();
        note.id = document.get("_id").toString();
        note.to = (String) document.get("to");
        note.say = (String) document.get("say");
        note.when = (String) document.get("when");
        note.from = (String) document.get("from");
        note.sent = (Boolean) document.get("sent");
        note.sentTo = (String) document.get("sentTo");
        
        return note;
    }
}