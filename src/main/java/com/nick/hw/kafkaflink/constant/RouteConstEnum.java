package com.nick.hw.kafkaflink.constant;

public enum RouteConstEnum {
    KAFKA_STREAMING_INBOUND_ENDPOINT("direct: kafka-inbound-endpoint"),
    MSG_PERSIST_ENDPOINT("seda: kafka-streaming-msgs-persist-endpoint"),
    FLINK_COMPUTE_ENDPOINT("direct: flink-compute-endpoint");


    private String endpoint;

    private RouteConstEnum(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
