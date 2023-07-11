package com.wicio.shiplog.vessel.application;


import com.wicio.shiplog.vessel.api.dto.CreateVesselRequest;
import com.wicio.shiplog.vessel.api.dto.CreateVesselResponse;
import com.wicio.shiplog.vessel.api.dto.CreateVesselResponseMapper;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.services.VesselCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVesselUseCase {

  private final VesselCreator vesselCreator;
  private final CreateVesselResponseMapper createVesselResponseMapper;

  public CreateVesselResponse createVessel(CreateVesselRequest request) {
    Vessel vessel = vesselCreator.apply(request.name(), request.productionDate());
    return createVesselResponseMapper.map(vessel);
  }
}
