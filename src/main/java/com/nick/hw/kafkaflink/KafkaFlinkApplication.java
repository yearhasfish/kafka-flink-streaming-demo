package com.nick.hw.kafkaflink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Nick for demo on kafka and flink
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.nick.hw.kafkaflink" })
@EnableJpaRepositories("com.nick.hw.kafkaflink.repository")
@EntityScan("com.nick.hw.kafkaflink.domain")
@ImportResource({"classpath*:camelContext.xml"})
public class KafkaFlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaFlinkApplication.class, args);
	}

}
