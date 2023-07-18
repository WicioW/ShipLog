package com.wicio.shiplog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class ShipLogApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShipLogApplication.class, args);
  }

  //TODO
//  @Bean
//  CommandLineRunner runner(KafkaTemplate<String, String> kafkaTemplate){
//    return args -> {
//
//      for (int i = 0; i < 100; i++) {
//        kafkaTemplate.send("vessel-log", "yo yo yo :" + i);
//      }
//
//    };
//  }
}
