package com.wicio.shiplog.log.api;

import static com.wicio.shiplog.kafka.KafkaTopicName.VESSEL_LOG;

import com.wicio.shiplog.log.domain.services.LogCreator;
import com.wicio.shiplog.route.producer.NewVesselLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewVesselLogEventListener {

  private final LogCreator logCreator;

  @Transactional
  @KafkaListener(topics = VESSEL_LOG, groupId = "vessel-log-group")
  public void handleNewVesselLogEvent(@Payload NewVesselLogEvent newVesselLogEvent) {
    log.info("Received: " + newVesselLogEvent);
    logCreator.apply(newVesselLogEvent.vesselId(),
        newVesselLogEvent.createLogRequest());
  }
}
