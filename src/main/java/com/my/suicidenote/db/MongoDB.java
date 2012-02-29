package com.my.suicidenote.db;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrii_Manuiev
 */
public class MongoDB {
    static final String HOST_NAME = "localhost";
    //static final String HOST_NAME = "mongodb-suicidenote.jelastic.servint.net";
    public static final String DB_NAME = "suicidedb";
    static final String USER_NAME = "webUser";
    static final String USER_PASSWORD = "xrokolo6574ss";
    
    private static DB db;

    public static synchronized DB getDB() {
        if (db == null) {
            try {
                db = new Mongo(HOST_NAME).getDB(DB_NAME);
                if (db.authenticate(USER_NAME, USER_PASSWORD.toCharArray())) {
                    System.out.println("Connection and authenticate is OK");
                } else {
                    System.out.println("Authenticate is failed");
                }
            } catch (Exception ex) {
                Logger.getLogger(MongoDB.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error : " + ex.toString());
            }
        }
        return db;
    }

    public static DBCollection findCollection(String collectionName) {
        DBCollection result = null;
        if (getDB() != null) {
            result = getDB().getCollection(collectionName);
        }
        return result;
    }
}
