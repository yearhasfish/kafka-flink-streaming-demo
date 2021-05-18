package com.nick.hw.kafkaflink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Nick for demo on kafka and flink
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.nick.hw.kafkaflink" })
@ImportResource({"classpath*:camelContext.xml"})
public class KafkaFlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaFlinkApplication.class, args);
	}

}
