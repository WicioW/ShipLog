package com.wicio.shiplog.route;

import com.wicio.shiplog.route.producer.NewVesselLogEvent;
import com.wicio.shiplog.route.producer.NewVesselLogEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
class RoutesCreator {

  private final NewVesselLogEventProducer newVesselLogEventProducer;
  private final InitialLogsForVesselsCreator initialLogsForVesselsCreator;
  private final SubsequentLogsForVesselsCreator subsequentLogsForVesselsCreator;

  //TODO scheduler
  @Scheduled(fixedRate = 10000)
 public void createRoutesForVessels() {
    log.info("Creating routes for vessels");
    List<NewVesselLogEvent> logs = initialLogsForVesselsCreator.execute();
    if (logs.isEmpty()) {
      logs = subsequentLogsForVesselsCreator.execute();
    }

    newVesselLogEventProducer.produceEvents(logs);
  }

}
