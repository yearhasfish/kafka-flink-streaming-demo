package com.nick.hw.kafkaflink.route;

import com.nick.hw.kafkaflink.constant.KakfaConnectionUtil;
import com.nick.hw.kafkaflink.constant.RouteConstEnum;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is just for Kafka POC, based on local docker environment
 * Nick
 */
@Component("kafkaStreamingRoute")
public class KafkaStreamingRoute extends SpringRouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(KafkaStreamingRoute.class);

    @Autowired
    private KakfaConnectionUtil kakfaConnectionUtil;

    private String upstreamDbConnectStr ;
    @Override
    public void configure() {
        upstreamDbConnectStr = kakfaConnectionUtil.retrieveUpstreamInboundConnection();
        logger.info("Kafka Streaming Route execution started, upstreamDbConnectStr = {}"
                , upstreamDbConnectStr);
        defineRoute();

    }

    private void defineRoute() {
        from(upstreamDbConnectStr)
                .to(RouteConstEnum.KAFKA_STREAMING_INBOUND_ENDPOINT.getEndpoint());

        from(RouteConstEnum.KAFKA_STREAMING_INBOUND_ENDPOINT.getEndpoint()).routeId("KafkaRoute")
                .log("Kafka inbound message received is : ${body}")
                .to(RouteConstEnum.MSG_PERSIST_ENDPOINT.getEndpoint())
                .log("Parallel computing while storing messages into DB")
                .to(RouteConstEnum.FLINK_COMPUTE_ENDPOINT.getEndpoint())
                .end();

    }
}
