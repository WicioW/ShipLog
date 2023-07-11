package com.wicio.shiplog.vessel.domain.services;

import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VesselCreator {

  private final VesselRepository vesselRepository;

  public Vessel apply(String name,
                      Instant productionDate) {
    Vessel vessel = Vessel.builder()
        .productionDate(productionDate)
        .name(name)
        .build();
    return vesselRepository.save(vessel);
  }
}
