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

    //TODO : change connection string
    static final String host = "mongodb-suicidenote.jelastic.servint.net";
    //static final String host = "localhost";
    public static final String dbname = "suicidedb";
    static final String user = "webUser";
    static final String passwd = "xrokolo6574ss";
    private static DB db;

    public static synchronized DB getDB() {
        if (db == null) {
            try {
                db = new Mongo(host).getDB(dbname);
                if (db.authenticate(user, passwd.toCharArray())) {
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
