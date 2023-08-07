package com.wicio.shiplog.route;

import com.wicio.shiplog.route.producer.NewVesselLogEvent;
import com.wicio.shiplog.route.producer.NewVesselLogEventProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoutesCreator {

  private final NewVesselLogEventProducer newVesselLogEventProducer;
  private final InitialLogsForVesselsCreator initialLogsForVesselsCreator;
  private final SubsequentLogsForVesselsCreator subsequentLogsForVesselsCreator;

  @Scheduled(fixedRate = 10000)
  public void createRoutesForVessels() {
    List<NewVesselLogEvent> logs = initialLogsForVesselsCreator.execute();
    if (logs.isEmpty()) {
      logs = subsequentLogsForVesselsCreator.execute();
    }
    if (logs.isEmpty()) {
      return;
    }
    log.info("Creating routes for vessels");
    newVesselLogEventProducer.produceEvents(logs);
  }

}
