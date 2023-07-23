package com.wicio.shiplog.kafka;

import com.wicio.shiplog.log.domain.services.LogCreator;
import com.wicio.shiplog.route.producer.NewVesselLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.wicio.shiplog.kafka.KafkaTopicName.VESSEL_LOG;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

  private final LogCreator logCreator;

  @Transactional
  @KafkaListener(topics = VESSEL_LOG, groupId = "vessel-log-group",containerFactory = "listenerFactory")
  public void newVesselLogListener(NewVesselLogEvent newVesselLogEvent){
    log.info("Received: " + newVesselLogEvent);
    logCreator.apply(newVesselLogEvent.vesselId(),
        newVesselLogEvent.createLogRequest());
  }
}
