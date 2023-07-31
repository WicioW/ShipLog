package com.wicio.shiplog;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

//@SpringBootTest(classes = KafkaProducerConsumerApplication.class)
@Testcontainers
public class KafkaTestContainers {

  @Container
  static KafkaContainer kafkaContainer = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:latest"));

  @DynamicPropertySource
  static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    registry.add("spring.datasource.url", () -> "jdbc:h2:mem:test");
    registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
    registry.add("spring.datasource.username", () -> "root");
    registry.add("spring.datasource.password", () -> "secret");
    registry.add("spring.flyway.enabled", () -> "false");
  }

}
