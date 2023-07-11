package com.wicio.shiplog.vessel.api.dto;

import com.wicio.shiplog.log.api.dto.LogResponse;
import java.time.Instant;
import lombok.Builder;

@Builder
public record VesselResponse(
    Long id,
    String name,
    Instant productionDate,
    LogResponse lastLog) {

}
