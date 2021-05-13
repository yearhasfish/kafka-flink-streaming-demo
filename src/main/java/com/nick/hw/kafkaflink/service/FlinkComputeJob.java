package com.nick.hw.kafkaflink.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.hw.kafkaflink.constant.KakfaConnectionUtil;
import com.nick.hw.kafkaflink.jsonobject.Customer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Count customers' changed time by flink
 */
@Service
public class FlinkComputeJob implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private KakfaConnectionUtil kakfaConnectionUtil;

    @Override public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kakfaConnectionUtil.retrieveKafkaAddress());
        props.setProperty("group.id", "flink-group");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>
                (kakfaConnectionUtil.getDownstreamFlinkTopicName(), new SimpleStringSchema(), props);
        DataStreamSource<String> kafkaStream = env.addSource(kafkaSource);
        DataStream<Tuple3<String, String, Integer>> changedCustOutputStream =
                kafkaStream.flatMap(new CustomerLastnameSplitter()).keyBy(value -> value.f0).sum(2);

        changedCustOutputStream.print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class CustomerLastnameSplitter implements FlatMapFunction<String, Tuple3<String, String, Integer>> {
    @Override public void flatMap(String s, Collector<Tuple3<String, String, Integer>> collector) throws Exception {
        Customer customer = new ObjectMapper().readValue(s, Customer.class);
        collector.collect(Tuple3.of(customer.getId(), "Customer " + customer.getLastName() + " changed times", 1));
    }
}
