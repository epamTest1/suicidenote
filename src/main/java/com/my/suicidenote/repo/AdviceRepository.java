package com.my.suicidenote.repo;

import com.my.suicidenote.dto.Advice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdviceRepository extends MongoRepository<Advice, Long> {

}
