package com.nick.hw.kafkaflink.tidb_repository;

import com.nick.hw.kafkaflink.tidb_domain.TiDBSourceKafkaTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiDBSourceKafkaTopicRepository extends CrudRepository<TiDBSourceKafkaTopic, Integer> {
}
