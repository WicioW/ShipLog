package com.wicio.shiplog.route.producer;

import static com.wicio.shiplog.kafka.KafkaTopicName.VESSEL_LOG;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewVesselLogEventProducer {

  private final KafkaTemplate<String, NewVesselLogEvent> kafkaTemplate;

  public void produceEvents(List<NewVesselLogEvent> events) {
    events.forEach(event -> {
      log.info("Sending: " + event);
      kafkaTemplate.send(VESSEL_LOG, event);
    });
  }

}

