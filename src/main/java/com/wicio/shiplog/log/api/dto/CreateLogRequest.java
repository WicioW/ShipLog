package com.wicio.shiplog.log.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateLogRequest(
    @NotNull
    @Min(-90)
    @Max(90)
    Double YCoordinate,
    @NotNull
    @Min(-180)
    @Max(180)
    Double XCoordinate,
    Integer speedOverGround,
    Integer courseOverGround,
    Integer windDirection,
    Integer windSpeed,
    Boolean stationary) {

}
