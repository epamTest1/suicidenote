package com.my.suicidenote.dto;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Andrii_Manuiev
 */
@Document(collection="advices")
public class Advice {
    public static final String DEFAULT_ADVICE_TEXT = "It is your responsibility to make your dreams come true";

    public static enum DB_FIELD_NAME {
        _id(), text();
    }
         
    @Id
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    public String getDEFAULT_ADVICE_TEXT() {
    	return DEFAULT_ADVICE_TEXT;
    }
}