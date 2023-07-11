package com.wicio.shiplog.log.domain.services;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.LogRepository;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.services.VesselFinder;
import com.wicio.shiplog.vessel.domain.services.VesselUpdater;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogCreator {

  private final LogRepository logRepository;
  private final PointCreator pointCreator;
  private final VesselUpdater vesselUpdater;
  private final VesselFinder vesselFinder;

  public Log apply(Long vesselId,
                   CreateLogRequest createLogRequest) {
    return getLog(vesselFinder.referenceById(vesselId), createLogRequest);
  }

  public Log apply(Vessel vessel,
                   CreateLogRequest createLogRequest) {
    return getLog(vessel, createLogRequest);
  }

  private Log getLog(Vessel vessel,
                     CreateLogRequest createLogRequest) {
    Point point =
        pointCreator.apply(createLogRequest.XCoordinate(), createLogRequest.YCoordinate());
    Log log =
        Log.builder()
            .vessel(vessel)
            .point(point)
            .speedOverGroundInKmPerHour(createLogRequest.speedOverGround())
            .courseOverGround(new Degree(createLogRequest.courseOverGround()))
            .windDirection(new Degree(createLogRequest.windDirection()))
            .windSpeedInKmPerHour(createLogRequest.windSpeed())
            .isStationary(createLogRequest.stationary())
            .build();
    vesselUpdater.updateLastLog(vessel, log);
    return logRepository.save(log);
  }

}
