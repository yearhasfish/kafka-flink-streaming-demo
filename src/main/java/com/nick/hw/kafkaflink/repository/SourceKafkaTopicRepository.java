package com.nick.hw.kafkaflink.repository;

import com.nick.hw.kafkaflink.domain.SourceKafkaTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceKafkaTopicRepository extends CrudRepository<SourceKafkaTopic, Integer> {
}
