package com.wicio.shiplog.vessel.api.dto;

import com.wicio.shiplog.log.api.dto.LogResponseMapper;
import com.wicio.shiplog.vessel.domain.Vessel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VesselResponseMapper {

  private final LogResponseMapper logResponseMapper;

  public VesselResponse map(Vessel vessel) {
    return VesselResponse.builder()
        .id(vessel.getId())
        .productionDate(vessel.getProductionDate())
        .name(vessel.getName())
        .lastLog(logResponseMapper.map(vessel.getLastLog()))
        .build();
  }
}
