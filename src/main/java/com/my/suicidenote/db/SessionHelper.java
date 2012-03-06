package com.my.suicidenote.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.my.suicidenote.db.object.Session;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrii_Manuiev
 */
public class SessionHelper {
    
    private static final String NOTES_COLLECTION_NAME = "sessions";
    private static DBCollection sessionsCollections = MongoDB.findCollection(NOTES_COLLECTION_NAME);

    public static void incertSession(Session session) {
        sessionsCollections.insert(session.toDbObject());
    }

    public static List<Session> getSessions() {
        List<Session> sessions = new ArrayList<Session>();

        DBCursor cursor = sessionsCollections.find();
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Session session = Session.fromDBObject(dbo);
            sessions.add(session);
        }
        cursor.close();
        return sessions;
    }
    
    public static List<Session> getSessions(BasicDBObject searchQuery) {
        List<Session> sessions = new ArrayList<Session>();

        DBCursor cursor = sessionsCollections.find(searchQuery);
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Session session = Session.fromDBObject(dbo);
            sessions.add(session);
        }
        return sessions;
    }
}