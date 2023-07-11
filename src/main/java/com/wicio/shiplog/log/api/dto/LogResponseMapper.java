package com.wicio.shiplog.log.api.dto;

import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.log.domain.Log;
import org.springframework.stereotype.Component;

@Component
public class LogResponseMapper {

  public LogResponse map(Log log) {
    return LogResponse.builder()
        .id(log.getId())
        .vesselId(log.getVessel()
            .getId())
        .geoPoint(log.getPoint())
        .speedOverGroundInKmPerHour(log.getSpeedOverGroundInKmPerHour())
        .courseOverGround(mapFromDegrees(log.getCourseOverGround()))
        .windDirection(mapFromDegrees(log.getWindDirection()))
        .windSpeedInKmPerHour(log.getWindSpeedInKmPerHour())
        .isStationary(log.isStationary())
        .build();
  }

  private Integer mapFromDegrees(Degree degree) {
    if (degree == null) {
      return null;
    }
    return degree.getValue();
  }
}
