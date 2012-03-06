package com.my.suicidenote.db.object;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author Andrii_Manuiev
 */
public class Session {

    static public enum DB_FIELD_NAME {

        _id(), timestamp(), ip();
    }
    private String id;
    private long timestamp;
    private String ip;

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
        public BasicDBObject toDbObject() {
        BasicDBObject document = new BasicDBObject();
        
        document.put(DB_FIELD_NAME.ip.name(), ip);
        document.put(DB_FIELD_NAME.timestamp.name(), timestamp);
        
        return document;
    }
    
    public static Session fromDBObject(DBObject document) {
        Session session = new Session();

        session.id = document.get(DB_FIELD_NAME._id.name()).toString();
        session.ip = (String) document.get(DB_FIELD_NAME.ip.name());
        session.timestamp = (Long) document.get(DB_FIELD_NAME.timestamp.name());
        
        return session;
    }

}