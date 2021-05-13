package com.nick.hw.kafkaflink.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakfaConnectionUtil {

    @Value("${mq.kafka.connect.hostname}")
    private String kafkaBrokerHostname;

    @Value("${mq.kafka.connect.port}")
    private String kafkaBrokerPort;

    @Value("${mq.kafka.connect.db_table_topic_name}")
    private String upstreamDbTableTopicName;

    @Value("${mq.kafka.connect.flink_compute_streaming_topic_name}")
    private String downstreamFlinkTopicName;

    public String retrieveKafkaAddress() {
        return new StringBuilder(kafkaBrokerHostname)
                .append(":").append(kafkaBrokerPort)
                .toString();
    }

    public String retrieveUpstreamInboundConnection() {
        String upstreamDbConnectStr = new StringBuilder("kafka:")
                .append(upstreamDbTableTopicName).append("?brokers=")
                .append(this.retrieveKafkaAddress())
                .toString();
        return upstreamDbConnectStr;
    }

    public String retrieveDownstreamOutboundConnection() {
        String downstreamFlinkConnectStr = new StringBuilder("kafka:")
                .append(downstreamFlinkTopicName).append("?brokers=")
                .append(this.retrieveKafkaAddress())
                .toString();
        return downstreamFlinkConnectStr;
    }

    public String getDownstreamFlinkTopicName() {
        return downstreamFlinkTopicName;
    }
}
