package com.wicio.shiplog.log.api.dto;

import lombok.Builder;

@Builder
public record CreateLogRequest(
    Double YCoordinate,
    Double XCoordinate,
    Double speedOverGround,
    Double courseOverGround,
    Double windDirection,
    Double windSpeed,
    Boolean stationary) {

}
