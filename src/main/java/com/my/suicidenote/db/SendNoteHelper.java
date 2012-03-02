package com.my.suicidenote.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.my.suicidenote.db.object.Note;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrii_Manuiev
 */
public class SendNoteHelper {
    
    private static final String NOTES_COLLECTION_NAME = "send_notes";
    private static DBCollection sendNotesCollections = MongoDB.findCollection(NOTES_COLLECTION_NAME);

    public static void incertNote(Note note) {
        sendNotesCollections.insert(note.toDbObject());
    }

    public static List<Note> getNotes() {
        List<Note> notes = new ArrayList<Note>();

        DBCursor cursor = sendNotesCollections.find();
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Note note = Note.fromDBObject(dbo);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public static List<Note> getNotes(BasicDBObject searchQuery) {
        List<Note> notes = new ArrayList<Note>();

        DBCursor cursor = sendNotesCollections.find(searchQuery);
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Note note = Note.fromDBObject(dbo);
            notes.add(note);
        }
        return notes;
    }
}