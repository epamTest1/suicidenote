package com.my.suicidenote.db.object;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author Andrii_Manuiev
 */
public class Advice {

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
    
    public BasicDBObject toDbObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put("text", text);
        
        return document;
    }
    
    public static Advice fromDBObject(DBObject document) {
        Advice advice = new Advice();
        advice.id = document.get("_id").toString();
        advice.text = (String) document.get("text");
        
        return advice;
    }
}