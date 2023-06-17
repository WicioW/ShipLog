package com.wicio.shiplog.log.api.dto;

import lombok.Builder;

@Builder
public record CreateLogRequest(
    Double YCoordinate,
    Double XCoordinate,
    Integer speedOverGround,
    Integer courseOverGround,
    Integer windDirection,
    Integer windSpeed,
    Boolean stationary) {

}
