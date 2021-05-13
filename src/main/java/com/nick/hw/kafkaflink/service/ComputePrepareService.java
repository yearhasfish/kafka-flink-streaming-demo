package com.nick.hw.kafkaflink.service;

import com.nick.hw.kafkaflink.route.DBSourceRoute;
import org.apache.camel.Exchange;
import org.apache.camel.json.simple.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComputePrepareService {

    private static Logger logger = LoggerFactory.getLogger(DBSourceRoute.class);

    public void prepareCompute(Exchange exchange) throws Exception {
        Map customerMap = exchange.getMessage().getBody(HashMap.class);
        String customerJson = new JsonObject(customerMap).toJson();
        logger.info("Compute content is {}", customerJson);
        exchange.getMessage().setBody(customerJson);
    }
}
