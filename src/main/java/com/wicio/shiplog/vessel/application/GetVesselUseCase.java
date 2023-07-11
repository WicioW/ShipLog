package com.wicio.shiplog.vessel.application;

import com.wicio.shiplog.vessel.api.dto.VesselResponse;
import com.wicio.shiplog.vessel.api.dto.VesselResponseMapper;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.services.VesselFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetVesselUseCase {

  private final VesselFinder vesselFinder;
  private final VesselResponseMapper vesselResponseMapper;

  public VesselResponse getVesselById(Long vesselId) {
    Vessel vessel = vesselFinder.byId(vesselId);
    return vesselResponseMapper.map(vessel);
  }

}
