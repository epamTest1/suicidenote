package com.my.suicidenote.db.object;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author Andrii_Manuiev
 */
public class Advice {

    public static enum DB_FIELD_NAME {
        _id(), text();
    }
            
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Advice() {    	
    }

    public Advice(String text) {
    	setText(text);
    }
    
    public BasicDBObject toDbObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put(DB_FIELD_NAME.text.name(), text);
        
        return document;
    }
    
    public static Advice fromDBObject(DBObject document) {
        Advice advice = new Advice();
        advice.id = document.get(DB_FIELD_NAME._id.name()).toString();
        advice.text = (String) document.get(DB_FIELD_NAME.text.name());
        
        return advice;
    }
}