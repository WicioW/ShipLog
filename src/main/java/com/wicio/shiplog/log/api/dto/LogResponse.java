package com.wicio.shiplog.log.api.dto;

import lombok.Builder;
import org.locationtech.jts.geom.Point;

@Builder
public record LogResponse(
    Long id,
    Long vesselId,
    Point geoPoint,
    Integer speedOverGroundInKmPerHour,
    Integer courseOverGround,
    Integer windDirection,
    Integer windSpeedInKmPerHour,
    boolean isStationary
) {

}
