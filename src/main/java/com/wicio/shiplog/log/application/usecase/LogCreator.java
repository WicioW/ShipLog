package com.wicio.shiplog.log.application.usecase;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.LogRepository;
import com.wicio.shiplog.vessel.domain.Vessel;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogCreator {

  private final LogRepository logRepository;
  private final PointCreator pointCreator;

  public Log apply(Vessel vessel, CreateLogRequest createLogRequest) {
    Point point =
        pointCreator.apply(createLogRequest.XCoordinate(), createLogRequest.YCoordinate());
    Log log =
        Log.builder()
            .vessel(vessel)
            .point(point)
            .speedOverGroundInKmPerHour(createLogRequest.speedOverGround())
            .courseOverGround(createLogRequest.courseOverGround())
            .windDirection(createLogRequest.windDirection())
            .windSpeedInKmPerHour(createLogRequest.windSpeed())
            .isStationary(createLogRequest.stationary())
            .build();

    return logRepository.save(log);
  }
}
