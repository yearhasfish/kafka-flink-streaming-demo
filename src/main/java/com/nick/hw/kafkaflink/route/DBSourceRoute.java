package com.nick.hw.kafkaflink.route;

import com.nick.hw.kafkaflink.constant.RouteConstEnum;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Persist Kafka streaming messages in real-time into TiDB from Mysql CDC
 * Nick
 */
@Component("dbSourceRoute")
public class DBSourceRoute extends SpringRouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(DBSourceRoute.class);

    @Override
    public void configure() {
        logger.info("DB Persistence Source Route execution started");
        defineRoute();
    }

    private void defineRoute() {
        from(RouteConstEnum.MSG_PERSIST_ENDPOINT.getEndpoint()).routeId("dbSourceRoute")
                .to("parseSourceObjectProcessor")
                .log("Kafka message is starting with persistence")
                .to("kafkaMsgsPersistProcessor");
    }
}
