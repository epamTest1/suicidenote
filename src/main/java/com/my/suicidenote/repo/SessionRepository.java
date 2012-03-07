package com.my.suicidenote.repo;

import com.my.suicidenote.dto.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Note, Long> {
	//List<Note> findByWhenLessThan(long when);	
}
