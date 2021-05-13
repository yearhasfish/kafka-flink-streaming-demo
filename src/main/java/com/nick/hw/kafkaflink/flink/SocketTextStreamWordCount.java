package com.nick.hw.kafkaflink.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * Standalone testing class for flink stream-based reading from Kafka topics
 */
public class SocketTextStreamWordCount {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:29092");
        props.setProperty("group.id", "flink-group");
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>
                ("fullfillment.inventory.customers", new SimpleStringSchema(), props);
        DataStreamSource<String> kafkaStream = env.addSource(kafkaSource);
        DataStream<Tuple2<String, Integer>> sumOutput = kafkaStream.flatMap(new Splitter()).keyBy(value -> value.f0).sum(1);

        sumOutput.print();
        env.execute("work count!");

    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word: sentence.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
