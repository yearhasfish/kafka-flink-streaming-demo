package com.nick.hw.kafkaflink.processor;

import com.nick.hw.kafkaflink.domain.SourceKafkaTopic;
import com.nick.hw.kafkaflink.repository.SourceKafkaTopicRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("kafkaMsgsPersistProcessor")
public class KafkaMsgsPersistProcessor implements Processor {

    private static Logger logger = LoggerFactory.getLogger(KafkaMsgsPersistProcessor.class);

    @Autowired
    private SourceKafkaTopicRepository sourceKafkaTopicRepository;

    @Override public void process(Exchange exchange) throws Exception {

        SourceKafkaTopic sourceKafkaTopic = exchange.getMessage().getBody(SourceKafkaTopic.class);
        sourceKafkaTopicRepository.save(sourceKafkaTopic);
        logger.info("Currently completed sending message to Postgres DB from Kafka streaming, row is {} "
                , sourceKafkaTopic);
    }
}
