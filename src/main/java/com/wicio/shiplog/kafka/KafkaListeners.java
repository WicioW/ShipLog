package com.wicio.shiplog.kafka;

import com.wicio.shiplog.log.domain.services.LogCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

  private final LogCreator logCreator;

  @KafkaListener(topics = "vessel-log", groupId = "vessel-log-group")
  void listener(String data){

    System.out.println("Received: " + data);

    //TODO
//    NewVesselLogDTO newVesselLogDTO = new NewVesselLogDTO();
//    logCreator.createLog(newVesselLogDTO.vesselId(),
//        newVesselLogDTO.createLogRequest());
  }
}
