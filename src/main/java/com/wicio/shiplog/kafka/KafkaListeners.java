package com.wicio.shiplog.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

  @KafkaListener(topics = "vessel-log", groupId = "vessel-log-group")
  void listener(String data){
    System.out.println("Received: " + data);
  }
}
