package com.wicio.shiplog.vessel.api.dto;

import com.wicio.shiplog.vessel.domain.Vessel;
import org.springframework.stereotype.Component;

@Component
public class CreateVesselResponseMapper {

  public CreateVesselResponse map(Vessel vessel) {
    return CreateVesselResponse.builder()
        .id(vessel.getId())
        .productionDate(vessel.getProductionDate())
        .name(vessel.getName())
        .build();
  }
}
