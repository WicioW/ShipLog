package com.wicio.shiplog.route;

import com.wicio.shiplog.route.util.dto.NewVesselLogDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
class RoutesCreator {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final InitialLogsForVesselsCreator initialLogsForVesselsCreator;
  private final SubsequentLogsForVesselsCreator subsequentLogsForVesselsCreator;


  //TODO scheduler
  void createRoutesForVessels() {
    List<NewVesselLogDTO> logs = initialLogsForVesselsCreator.execute();
    if (logs.isEmpty()) {
      logs = subsequentLogsForVesselsCreator.execute();
    }

    sendToKafka(logs);
  }

  private void sendToKafka(List<NewVesselLogDTO> logs) {
    logs.forEach(log -> kafkaTemplate.send("vessel-log", log.toString()));
  }

}
