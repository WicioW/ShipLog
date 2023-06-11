package com.wicio.shiplog.vessel.application.usecase;

import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VesselUpdater {

  private final VesselRepository vesselRepository;

  public Vessel updateLastLog(Vessel vessel, Log log) {
    vessel.updateLastLog(log);
    return vesselRepository.save(vessel);
  }
}
