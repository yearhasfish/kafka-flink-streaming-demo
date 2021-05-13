package com.nick.hw.kafkaflink.route;

import com.nick.hw.kafkaflink.constant.KakfaConnectionUtil;
import com.nick.hw.kafkaflink.constant.RouteConstEnum;
import com.nick.hw.kafkaflink.service.ComputePrepareService;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("flinkComputeRoute")
public class FlinkComputeRoute extends SpringRouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(DBSourceRoute.class);

    private static String JSON_PATH_OF_CUSTOMERS_CDC = "$..after";

    @Autowired
    private KakfaConnectionUtil kakfaConnectionUtil;

    @Autowired
    private ComputePrepareService emailComputeService;

    @Override
    public void configure() {
        logger.info("Register a new flink compute Route.");
        defineRoute();
    }

    private void defineRoute() {
        from(RouteConstEnum.FLINK_COMPUTE_ENDPOINT.getEndpoint())
                .routeId("flinkRoute").split().jsonpath(JSON_PATH_OF_CUSTOMERS_CDC)
                .log("CDC data is " + body())
                .bean(ComputePrepareService.class, "prepareCompute")
                .to(kakfaConnectionUtil.retrieveDownstreamOutboundConnection());
    }
}
