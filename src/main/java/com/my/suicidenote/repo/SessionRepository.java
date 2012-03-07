package com.my.suicidenote.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.my.suicidenote.dto.Session;

public interface SessionRepository extends MongoRepository<Session, Long> {
	List<Session> findByWhenGreaterThan(long when);	
}
