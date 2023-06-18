package com.wicio.shiplog.route;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.application.usecase.LogCreator;
import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import com.wicio.shiplog.route.util.TimeDifferenceCalculator;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoutesCreator {

  private final LogCreator logCreator;
  private final VesselRepository vesselRepository;
  private final TimeDifferenceCalculator timeDifferenceCalculator;
  private final RandomNumberGenerator randomNumberGenerator;
  private final DistanceCalculatorBasedOnSpeedAndTime distanceCalculatorBasedOnSpeedAndTime;
  private final DirectionGeneratorBasedOnDirectionMinutesAgo directionGeneratorBasedOnDirectionMinutesAgo;
  private final SpeedGenerator speedGenerator;

  private final SpeedGeneratorConfigVO speedOverGroundCongifVO =
      new SpeedGeneratorConfigVO(0, 20, 20);

  private final SpeedGeneratorConfigVO windSpeedConfigVO =
      new SpeedGeneratorConfigVO(0, 40, 40);

  private static final Degree startingDirection = new Degree(270);

  private static final Double ONE_MONTH_IN_MINUTES = 44640d;

  public void createLogForVessels() {
    Instant timeStamp = Instant.now();
    log.debug("-= SCHEDULER STARTING:" + timeStamp.toString() + " =-");

    List<Vessel> allVessels = vesselRepository.findAll();
    for (Vessel vessel : allVessels) {
      log.debug("-= NEXT SHIP =-");
      Log lastLog = vessel.getLastLog();
      Log newLog;
      if (lastLog == null
          || isMoreThanOneMonthBetweenInstants(timeStamp, lastLog.getCreated())) {
        newLog = createInitialLogForVessel(vessel, timeStamp);
      } else {
        Instant lastTimeStamp = lastLog.getCreated();

        int newSog =
            speedGenerator.generateSpeedOverGround(
                speedOverGroundCongifVO,
                lastLog.getSpeedOverGroundInKmPerHour(),
                timeDifferenceCalculator.minutesBetween(timeStamp, lastTimeStamp));
        Degree newCog =
            directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
                lastLog.getCourseOverGround(),
                timeDifferenceCalculator.minutesBetween(timeStamp, lastTimeStamp));

        Double[] newCoords =
            coordinatesFromStartPointAndDistanceAndBearing(
                lastLog.getPoint()
                    .getY(),
                lastLog.getPoint()
                    .getX(),
                distanceCalculatorBasedOnSpeedAndTime.distanceInMetres(newSog,
                    timeDifferenceCalculator.minutesBetween(timeStamp,
                        lastTimeStamp)),
                newCog.getValue());
        newLog =
            logCreator.apply(
                vessel,
                CreateLogRequest.builder()
                    .YCoordinate(newCoords[0])
                    .XCoordinate(newCoords[1])
                    .speedOverGround(newSog)
                    .courseOverGround(newCog.getValue())
                    .windDirection(
                        directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
                                lastLog.getWindDirection(),
                                timeDifferenceCalculator.minutesBetween(timeStamp, lastTimeStamp)
                            )
                            .getValue())
                    .windSpeed(
                        speedGenerator.generateSpeedOverGround(
                            windSpeedConfigVO,
                            lastLog.getWindSpeedInKmPerHour(),
                            timeDifferenceCalculator.minutesBetween(timeStamp, lastTimeStamp)))
                    .stationary(false)
                    .build()
            );

        log.debug("Created " + newLog.toLogString() + " for " + newLog.getVessel()
            .toLogString());
      }
    }
  }

  private boolean isMoreThanOneMonthBetweenInstants(Instant timeStamp,
                                                    Instant secondTimestamp) {
    return timeDifferenceCalculator.minutesBetween(secondTimestamp, timeStamp)
        > ONE_MONTH_IN_MINUTES;
  }

  /**
   * latitude, longitude - entry point coordinates distanceInMetres - distance that you want to move
   * the point by bearing - an angle, direction towards which you want to move the point. 0 is
   * towards the North, 90 - East, 180 - South, 270 - West. And all between, i.e. 45 is North East.
   * earthRadiusInMetres - Earth radius in metres.
   */
  private Double[] coordinatesFromStartPointAndDistanceAndBearing(
      double latitude,
      double longitude,
      double distanceInMetres,
      double bearing) {
    double brngRad = toRadians(bearing);
    double latRad = toRadians(latitude);
    double lonRad = toRadians(longitude);
    int earthRadiusInMetres = 6371000;
    double distFrac = distanceInMetres / earthRadiusInMetres;

    double latitudeResult =
        asin(sin(latRad) * cos(distFrac) + cos(latRad) * sin(distFrac) * cos(brngRad));
    double a =
        atan2(
            sin(brngRad) * sin(distFrac) * cos(latRad),
            cos(distFrac) - sin(latRad) * sin(latitudeResult));
    double longitudeResult = (lonRad + a + 3 * PI) % (2 * PI) - PI;

    log.debug("before: latitude: " + latitude + ", longitude: " + longitude);
    log.debug(latitude + ", " + longitude);
    log.debug(
        "latitude: " + toDegrees(latitudeResult) + ", longitude: " + toDegrees(longitudeResult));
    log.debug(toDegrees(latitudeResult) + ", " + toDegrees(longitudeResult));
    return new Double[]{toDegrees(latitudeResult), toDegrees(longitudeResult)};
  }

  private Log createInitialLogForVessel(Vessel vessel,
                                        Instant currentTimeStamp) {
    String[] coords = randomStartCoordinates();
    log.debug("start point coords:" + coords[0] + ", " + coords[1]);
    Instant lastTimeStamp = currentTimeStamp.minus(2, ChronoUnit.MINUTES);
    int speedOverGround = speedGenerator.generateSpeedOverGround(
        speedOverGroundCongifVO,
        0,
        timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
    Degree courseOverGround =
        directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
            startingDirection,
            timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
    Degree windDirection =
        directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
            new Degree(
                randomNumberGenerator.randomIntBetween(0, 360)),
            timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
    int windSpeed = speedGenerator.generateSpeedOverGround(
        windSpeedConfigVO,
        20,
        timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp)
    );

    Boolean stationary = false;

    return logCreator.apply(
        vessel,
        CreateLogRequest.builder()
            .YCoordinate(Double.parseDouble(coords[0]))
            .XCoordinate(Double.parseDouble(coords[1]))
            .speedOverGround(speedOverGround)
            .courseOverGround(courseOverGround.getValue())
            .windDirection(windDirection.getValue())
            .windSpeed(windSpeed)
            .stationary(stationary)
            .build()
    );
  }

}
