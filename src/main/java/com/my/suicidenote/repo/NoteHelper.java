package com.my.suicidenote.repo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.my.suicidenote.dto.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrii_Manuiev
 */
public class NoteHelper {
    
    private static final String NOTES_COLLECTION_NAME = "notes";
    private static DBCollection notesCollections = MongoDB.findCollection(NOTES_COLLECTION_NAME);

    public static void incertNote(Note note) {
        notesCollections.insert(note.toDbObject());
    }

    public static List<Note> getNotes() {
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
        notesCollections.update(filter, newDocument);
    }
    
    public static void removeNote(BasicDBObject document) {
        notesCollections.findAndRemove(document);
    }
    
    public static void removeNote(Note note) {
        notesCollections.findAndRemove(note.toDbObject());
    }
}
