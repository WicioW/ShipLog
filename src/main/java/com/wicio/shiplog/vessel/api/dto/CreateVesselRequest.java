package com.wicio.shiplog.vessel.api.dto;

import java.time.Instant;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateVesselRequest(
    @NotNull String name,
    Instant productionDate
) {

}
