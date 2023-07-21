package com.wicio.shiplog.route.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewVesselLogEventProducer {


  private final KafkaTemplate<String, String> kafkaTemplate;

  public void produceEvents(List<NewVesselLogEvent> logs) {
    logs.forEach(log -> kafkaTemplate.send("vessel-log", log.toString()));
  }

}
