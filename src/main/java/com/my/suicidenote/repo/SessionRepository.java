package com.my.suicidenote.repo;

import com.my.suicidenote.dto.Session;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SessionRepository extends MongoRepository<Session, Long> {
    
	List<Session> findByTimestampGreaterThan(long timestamp);
        
        List<Session> findByIp(String ip);
        
        @Query("{ 'ip' : 0, 'timestamp' : {$gt : 1} }")
        List<Session> findByIpAndTimestampGreaterThan(String ip, long timestamp);
}