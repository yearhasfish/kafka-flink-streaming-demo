package com.nick.hw.kafkaflink.processor;

import com.nick.hw.kafkaflink.domain.SourceKafkaTopic;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("parseSourceObjectProcessor")
public class ParseSourceObjectProcessor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(ParseSourceObjectProcessor.class);

    @Value("${mq.kafka.connect.db_table_topic_name}")
    private String upstreamDbTableTopicName;

    @Override public void process(Exchange exchange) throws Exception {
        logger.info("Parser Source Processor is receiving an exchange msgId as {}",
                exchange.getMessage().getMessageId());
        SourceKafkaTopic sourceKafkaTopic = new SourceKafkaTopic();
        sourceKafkaTopic.setTopicName(upstreamDbTableTopicName);
        sourceKafkaTopic.setSrcPayload(exchange.getMessage().getBody(String.class));
        exchange.getMessage().setBody(sourceKafkaTopic);
    }
}
