package com.wicio.shiplog.tmp;

import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbFiller {

  private final VesselRepository vesselRepository;

  @PostConstruct
  public void fillDb() {
    log.info("Filling DB");
    createVessels();
  }

  private void createVessels() {
    long count = vesselRepository.count();
    if(count > 0) {
      log.info("DB is already filled");
      return;
    }

    Vessel vessel = Vessel.builder()
        .name("test vessel name")
        .productionDate(Instant.now())
        .build();
    vesselRepository.save(vessel);
    log.info("Vessel created: " + vessel.toLogString());
  }

}
