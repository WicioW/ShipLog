package com.wicio.shiplog.route;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import com.wicio.shiplog.route.util.RouteInitialPoint;
import com.wicio.shiplog.route.util.TimeDifferenceCalculator;
import com.wicio.shiplog.route.producer.NewVesselLogEvent;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InitialLogsForVesselsCreator {

  private final VesselRepository vesselRepository;
  private final TimeDifferenceCalculator timeDifferenceCalculator;
  private final RandomNumberGenerator randomNumberGenerator;
  private final DirectionGeneratorBasedOnDirectionMinutesAgo directionGeneratorBasedOnDirectionMinutesAgo;
  private final SpeedGenerator speedGenerator;

  private final SpeedGeneratorConfigDTO speedGeneratorConfigDTO =
      new SpeedGeneratorConfigDTO(0, 20, 20);

  private final SpeedGeneratorConfigDTO windSpeedConfigVO =
      new SpeedGeneratorConfigDTO(0, 40, 40);

  private static final Degree startingDirection = new Degree(270);

  List<NewVesselLogEvent> execute() {
    List<Vessel> vessels = vesselRepository.findAllByLastLogIsNull();
    if(vessels.isEmpty()) {
      return List.of();
    }
    List<Point> points = RouteInitialPoint.pointsList();

    int pointsListSize = points.size();

    Instant currentTimeStamp;
    Instant lastTimeStamp;

    int speedOverGround;
    Degree courseOverGround;
    Degree windDirection;
    int windSpeed;

    int indexOfPointsList = 0;
    ArrayList<NewVesselLogEvent> list = new ArrayList<>();
    for (Vessel vessel : vessels) {
      Point point = points.get(indexOfPointsList % pointsListSize);

      currentTimeStamp = Instant.now();
      lastTimeStamp = currentTimeStamp.minus(10, ChronoUnit.MINUTES);
      speedOverGround = speedGenerator.generateSpeedOverGround(
          speedGeneratorConfigDTO,
          0,
          timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
      courseOverGround =
          directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
              startingDirection,
              timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
      windDirection =
          directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
              new Degree(
                  randomNumberGenerator.randomIntBetween(0, 360)),
              timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
      windSpeed = speedGenerator.generateSpeedOverGround(
          windSpeedConfigVO,
          20,
          timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));

      list.add(new NewVesselLogEvent(
          vessel.getId(),
          CreateLogRequest.builder()
              .YCoordinate(point.getY())
              .XCoordinate(point.getX())
              .speedOverGround(speedOverGround)
              .courseOverGround(courseOverGround.getValue())
              .windDirection(windDirection.getValue())
              .windSpeed(windSpeed)
              .stationary(false)
              .build()
      ));
      indexOfPointsList++;
    }
    return list;
  }

}
