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
public class NoteHelper {
    
    private static final String NOTES_COLLECTION_NAME = "notes";
    private static DBCollection notesCollections;

    public static void init() {
        if (notesCollections == null) {
            notesCollections = MongoDB.findCollection(NOTES_COLLECTION_NAME);
        }
    }
    
    public static void incertNote(Note note) {
        init();
        notesCollections.insert(note.toDbObject());
    }

    public static List<Note> getNotes() {
        init();
        List<Note> notes = new ArrayList<Note>();

        DBCursor cursor = notesCollections.find();
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Note note = Note.fromDBObject(dbo);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public static List<Note> getNotes(BasicDBObject searchQuery) {
        init();
        List<Note> notes = new ArrayList<Note>();

        DBCursor cursor = notesCollections.find(searchQuery);
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Note note = Note.fromDBObject(dbo);
            notes.add(note);
        }
        return notes;
    }
    
    public static void updateNote(BasicDBObject filter, BasicDBObject newDocument) {
        init();
        notesCollections.update(filter, newDocument);
    }
    
    public static void removeNote(BasicDBObject document) {
        init();
        notesCollections.findAndRemove(document);
    }
}
