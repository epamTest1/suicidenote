package com.my.suicidenote.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.my.suicidenote.dto.Advice;

public interface AdviceRepository extends MongoRepository<Advice, Long> {

}
