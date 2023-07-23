package com.wicio.shiplog.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.wicio.shiplog.kafka.KafkaTopicName.VESSEL_LOG;

@Configuration
public class KafkaTopicConfig {

  @Bean
  public NewTopic vesselLogTopic() {
    return TopicBuilder
        .name(VESSEL_LOG)
        .partitions(1)
        .replicas(1)
        .build();
  }
}
