package com.my.suicidenote.repo;

import com.my.suicidenote.dto.Note;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, Long>, NoteCustomRepository {
	List<Note> findByWhenLessThan(long when);	
}
