package com.wicio.shiplog.vessel.api.dto;

import java.time.Instant;
import lombok.Builder;

@Builder
public record CreateVesselRequest(
    String name,
    Instant productionDate
) {

}
