package com.my.suicidenote.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.my.suicidenote.dto.Note;

public interface NoteRepository extends MongoRepository<Note, Long> {

}
