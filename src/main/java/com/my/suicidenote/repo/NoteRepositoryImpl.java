package com.my.suicidenote.repo;

import com.my.suicidenote.dto.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class NoteRepositoryImpl implements NoteCustomRepository {
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void moveNoteToSent(Note note) {
            mongoTemplate.remove(note, "notes");
            mongoTemplate.insert(note, "sent_notes");
	}
}
