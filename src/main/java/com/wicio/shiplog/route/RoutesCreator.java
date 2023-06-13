//package com.wicio.shiplog.route;
//
//import static java.lang.Math.PI;
//import static java.lang.Math.asin;
//import static java.lang.Math.atan2;
//import static java.lang.Math.cos;
//import static java.lang.Math.sin;
//import static java.lang.Math.toDegrees;
//import static java.lang.Math.toRadians;
//
//import com.wicio.shiplog.log.api.dto.CreateLogRequest;
//import com.wicio.shiplog.log.application.usecase.LogCreator;
//import com.wicio.shiplog.log.domain.Degree;
//import com.wicio.shiplog.log.domain.Log;
//import com.wicio.shiplog.route.util.RandomNumberGenerator;
//import com.wicio.shiplog.route.util.TimeDifferenceCalculator;
//import com.wicio.shiplog.vessel.domain.Vessel;
//import com.wicio.shiplog.vessel.domain.VesselRepository;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Random;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class RoutesCreator {
//
//  private final LogCreator logCreator;
//  private final VesselRepository vesselRepository;
//  private final TimeDifferenceCalculator timeDifferenceCalculator;
//  private final RandomNumberGenerator randomNumberGenerator;
//  private final DistanceCalculatorBasedOnSpeedAndTime distanceCalculatorBasedOnSpeedAndTime;
//  private final DirectionGeneratorBasedOnDirectionMinutesAgo directionGeneratorBasedOnDirectionMinutesAgo;
//
//  private static final Double[] WIND_SPEED_RANGE = {0d, 40d};
//
//  private static final double MAXIMAL_WIND_SPEED_CHANGE_PER_HOUR = 40;
//  /**
//   * Speed over ground range in km/h
//   */
//  private static final Double[] SPEED_OVER_GROUND_RANGE = {0d, 20d};
//
//  private static final double MAXIMAL_SPEED_OVER_GROUND_CHANGE_PER_HOUR = 20;
////  // To prevent ships from going in circles -> go left
////  private static final Double[] IMPOSED_SHIP_COURSE_RANGE = {180d, 360d};
//
//  //  private static final Double STARTING_DIRECTION = 270d;
//  private static final Degree startingDirection = new Degree(270);
//
//  private static final Double ONE_MONTH_IN_MINUTES = 44640d;
//
//  public void createLogForVessels() {
//    Instant timeStamp = Instant.now();
//    log.debug("-= SCHEDULER STARTING:" + timeStamp.toString() + " =-");
//
//    List<Vessel> allVessels = vesselRepository.findAll();
//    for (Vessel vessel : allVessels) {
//      log.debug("-= NEXT SHIP =-");
//      Log lastLog = vessel.getLastLog();
//      Log newLog;
//      if (lastLog == null
//          ||
//          timeDifferenceCalculator.minutesBetween(lastLog.getCreated(), timeStamp)
//              > ONE_MONTH_IN_MINUTES) {
//        newLog = createInitialLogForVessel(vessel, timeStamp);
//      } else {
//        Instant lastTimeStamp = lastLog.getCreated();
//
//        Double newSog =
//            generateSpeedOverGround(
//                timeStamp,
//                lastLog.getSpeedOverGroundInKmPerHour(),
//                lastTimeStamp);
//
//        Degree newCog =
//            directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
//                lastLog.getCourseOverGround(),
//                timeDifferenceCalculator.minutesBetween(timeStamp, lastTimeStamp));
//
//        Double[] newCoords =
//            coordinatesFromStartPointAndDistanceAndBearing(
//                lastLog.getPoint()
//                    .getY(),
//                lastLog.getPoint()
//                    .getX(),
//                distanceCalculatorBasedOnSpeedAndTime.distanceInMetres(newSog,
//                    timeDifferenceCalculator.minutesBetween(timeStamp,
//                        lastTimeStamp)),
//                newCog.getValue());
//        newLog =
//            logCreator.apply(
//                vessel,
//                CreateLogRequest.builder()
//                    .YCoordinate(newCoords[0])
//                    .XCoordinate(newCoords[1])
//                    .speedOverGround(newSog)
//                    .courseOverGround(newCog.getValue())
//                    .windDirection(
//                        directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
//                                lastLog.getWindDirection(),
//                                timeDifferenceCalculator.minutesBetween(timeStamp, lastTimeStamp)
//                            )
//                            .getValue())
//                    .windSpeed(generateWindSpeed(
//                        timeStamp,
//                        lastLog.getWindSpeedInKmPerHour(),
//                        lastTimeStamp))
//                    .stationary(false)
//                    .build()
//            );
//
//        log.debug("Created " + newLog.toLogString() + " for " + newLog.getVessel()
//            .toLogString());
//      }
//    }
//  }
//
//  /**
//   * latitude, longitude - entry point coordinates distanceInMetres - distance that you want to move
//   * the point by bearing - an angle, direction towards which you want to move the point. 0 is
//   * towards the North, 90 - East, 180 - South, 270 - West. And all between, i.e. 45 is North East.
//   * earthRadiusInMetres - Earth radius in metres.
//   */
//  private Double[] coordinatesFromStartPointAndDistanceAndBearing(
//      double latitude,
//      double longitude,
//      double distanceInMetres,
//      double bearing) {
//    double brngRad = toRadians(bearing);
//    double latRad = toRadians(latitude);
//    double lonRad = toRadians(longitude);
//    int earthRadiusInMetres = 6371000;
//    double distFrac = distanceInMetres / earthRadiusInMetres;
//
//    double latitudeResult =
//        asin(sin(latRad) * cos(distFrac) + cos(latRad) * sin(distFrac) * cos(brngRad));
//    double a =
//        atan2(
//            sin(brngRad) * sin(distFrac) * cos(latRad),
//            cos(distFrac) - sin(latRad) * sin(latitudeResult));
//    double longitudeResult = (lonRad + a + 3 * PI) % (2 * PI) - PI;
//
//    log.debug("before: latitude: " + latitude + ", longitude: " + longitude);
//    log.debug(latitude + ", " + longitude);
//    log.debug(
//        "latitude: " + toDegrees(latitudeResult) + ", longitude: " + toDegrees(longitudeResult));
//    log.debug(toDegrees(latitudeResult) + ", " + toDegrees(longitudeResult));
//    return new Double[]{toDegrees(latitudeResult), toDegrees(longitudeResult)};
//  }
//
//  public static double randomGaussian(int min,
//                                      int max) {
//    log.debug("random gausian");
//    log.debug("min:" + min + ", max:" + max);
//    int mean = (min + max) / 2;
//    int deviation =
//        min != max
//            ? (max - min) / 3
//            : max / 3; // About 99.7% of a population is within +/- 3 standard deviations so divided
//    // by 3
//    log.debug("deviation:" + deviation);
//    Random random = new Random();
//    double gaussian = random.nextGaussian() * deviation + mean;
//    if (gaussian > max) {
//      return max;
//    }
//    if (gaussian < min) {
//      return min;
//    }
//    return gaussian;
//  }
//
////  private List<String> coordinateStartingPoints = new ArrayList<>();
//
////  private String[] randomStartCoordinates() {
////    if (coordinateStartingPoints == null || coordinateStartingPoints.isEmpty()) {
////      coordinateStartingPoints = new ArrayList<>(Arrays.asList(COORDINATES_LIST));
////    }
////    String startingPoint =
////        coordinateStartingPoints.get(
////            randomNumberGenerator.randomIntBetween(0, coordinateStartingPoints.size() - 1));
////    coordinateStartingPoints.remove(startingPoint);
////    return startingPoint
////        .replace(" ", "")
////        .split(",");
////  }
//
//  private Log createInitialLogForVessel(Vessel vessel,
//                                        Instant currentTimeStamp) {
//    String[] coords = randomStartCoordinates();
//    log.debug("start point coords:" + coords[0] + ", " + coords[1]);
//    Instant lastTimeStamp = currentTimeStamp.minus(2, ChronoUnit.MINUTES);
//    Double speedOverGround = generateSpeedOverGround(currentTimeStamp, 0d, lastTimeStamp);
//    Degree courseOverGround =
//        directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
//            startingDirection,
//            timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
//    Degree windDirection =
//        directionGeneratorBasedOnDirectionMinutesAgo.generateDirection(
//            new Degree(
//                randomNumberGenerator.randomIntBetween(0, 360)),
//            timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp));
//    Double windSpeed = generateWindSpeed(currentTimeStamp, 20d, lastTimeStamp);
//    Boolean stationary = false;
//
//    return logCreator.apply(
//        vessel,
//        CreateLogRequest.builder()
//            .YCoordinate(Double.parseDouble(coords[0]))
//            .XCoordinate(Double.parseDouble(coords[1]))
//            .speedOverGround(speedOverGround)
//            .courseOverGround(courseOverGround.getValue())
//            .windDirection(windDirection.getValue())
//            .windSpeed(windSpeed)
//            .stationary(stationary)
//            .build()
//    );
//  }
//
//  // normal direction generation, vessel can go in circles
//  /*
//    private Double generateDirection1(
//        Instant currentTimeStamp, Double lastDirection, Instant lastTimeStamp) {
//      if (lastDirection == null) {
//        return randomDouble(0d, 360d);
//      }
//      long minutesPassed = minutesBetween(currentTimeStamp, lastTimeStamp);
//      Double[] range = directionRangeForTimeDifference(lastDirection, minutesPassed);
//      log.debug("dir:" + range[0] + " " + range[1]);
//      Double direction = randomDouble(range[0], range[1]);
//      log.debug("generated direction:" + direction);
//      log.debug("normalized direction:" + directionWithinNormalizedBounds(direction));
//      return directionWithinNormalizedBounds(direction);
//    }
//  */
////  private Double generateDirection(
////      Instant currentTimeStamp,
////      Double lastDirection,
////      Instant lastTimeStamp) {
////    if (lastDirection == null) {
////      return randomNumberGenerator.randomDoubleBetween(IMPOSED_SHIP_COURSE_RANGE[0],
////          IMPOSED_SHIP_COURSE_RANGE[1]);
////    }
////    long minutesPassed = timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp);
////    Double[] range = rangeForImposedCourseRange(minutesPassed, lastDirection);
////    log.debug("range:" + range[0] + " " + range[1]);
////    Double direction = randomNumberGenerator.randomDoubleBetween(range[0], range[1]);
////    log.debug("generated direction:" + direction);
////    return direction;
////  }
////
////  private static Double[] rangeForImposedCourseRange(long minutesPassed,
////                                                     double lastDirection) {
////    long possibleTurnRange = minutesPassed;
////    if (possibleTurnRange >= IMPOSED_SHIP_COURSE_RANGE[1] - IMPOSED_SHIP_COURSE_RANGE[0]) {
////      return new Double[]{IMPOSED_SHIP_COURSE_RANGE[0], IMPOSED_SHIP_COURSE_RANGE[1]};
////    }
////    Double max;
////    Double min;
////    // górna granica
////    if (lastDirection + (possibleTurnRange) / 2 > IMPOSED_SHIP_COURSE_RANGE[1]) {
////      max = IMPOSED_SHIP_COURSE_RANGE[1];
////      min = IMPOSED_SHIP_COURSE_RANGE[1] - possibleTurnRange;
////      return new Double[]{min, max};
////    }
////    // dolna granica
////    if (lastDirection - (possibleTurnRange / 2) < IMPOSED_SHIP_COURSE_RANGE[0]) {
////      min = IMPOSED_SHIP_COURSE_RANGE[0];
////      max = IMPOSED_SHIP_COURSE_RANGE[0] + possibleTurnRange;
////      return new Double[]{min, max};
////    }
////
////    min = lastDirection - (possibleTurnRange / 2);
////    max = lastDirection + (possibleTurnRange / 2);
////
////    return new Double[]{min, max};
////  }
//
//  // added to prevent vessels from going in circles
////  private Double directionWithinVesselCourseRange(Double direction) {
////    if (direction < IMPOSED_SHIP_COURSE_RANGE[0]) {
////      return IMPOSED_SHIP_COURSE_RANGE[0];
////    }
////    if (direction > IMPOSED_SHIP_COURSE_RANGE[1]) {
////      return IMPOSED_SHIP_COURSE_RANGE[1];
////    }
////    return direction;
////  }
//
////  private Double directionWithinNormalizedBounds(Double direction) {
////    while (direction < 0) {
////      direction += 360;
////    }
////    while (direction > 360) {
////      direction -= 360;
////    }
////    return direction;
////  }
//
////  /**
////   * range is not between 0 and 360. Meaning values can be smaller than 0 and bigger than 360.
////   */
////  private Double[] directionRangeForTimeDifference(Double lastDirection,
////                                                   long minutes) {
////    Double radius = (double) minutes;
////    return new Double[]{lastDirection - radius, lastDirection + radius};
////  }
//
//  private Double generateWindSpeed(
//      Instant currentTimeStamp,
//      Double lastWindSpeed,
//      Instant lastTimeStamp) {
//    long minutesPassed = timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp);
//    Double[] range = windSpeedRangeForTimeDifference(lastWindSpeed, minutesPassed);
//    log.debug("ws min" + range[0] + ", max: " + range[1]);
//    return randomGaussian((int) Math.round(range[0]), (int) Math.round(range[1]));
//  }
//
//  private Double[] windSpeedRangeForTimeDifference(Double lastWindSpeed,
//                                                   long minutes) {
//    if (lastWindSpeed == null) {
//      return WIND_SPEED_RANGE;
//    }
//    Double radius = (double) (MAXIMAL_WIND_SPEED_CHANGE_PER_HOUR * minutes) / 120;
//    Double min =
//        (lastWindSpeed - radius < WIND_SPEED_RANGE[0])
//            ? WIND_SPEED_RANGE[0]
//            : lastWindSpeed - radius;
//
//    Double max =
//        (lastWindSpeed + radius) > WIND_SPEED_RANGE[1]
//            ? WIND_SPEED_RANGE[1]
//            : lastWindSpeed + radius;
//    log.debug("ws range, min:" + min + ", max:" + max);
//    return new Double[]{min, max};
//  }
//
//  private Double generateSpeedOverGround(
//      Instant currentTimeStamp,
//      Double lastSpeedOverGround,
//      Instant lastTimeStamp) {
//    if (lastSpeedOverGround == null) {
//      return randomNumberGenerator.randomDoubleBetween(SPEED_OVER_GROUND_RANGE[0],
//          SPEED_OVER_GROUND_RANGE[1]);
//    }
//
//    long minutesPassed = timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp);
//    log.debug("sog, minutes passed:" + minutesPassed);
//    Double[] range = speedOverGroundRangeForTimeDifference(lastSpeedOverGround, minutesPassed);
//    log.debug("sog:" + range[0] + " " + range[1]);
//    return randomGaussian((int) Math.round(range[0]), (int) Math.round(range[1]));
//  }
//
//  private Double[] speedOverGroundRangeForTimeDifference(Double lastSpeedOverGround,
//                                                         long minutes) {
//    Double radius = (double) (MAXIMAL_SPEED_OVER_GROUND_CHANGE_PER_HOUR * minutes) / 120;
//    Double min =
//        (lastSpeedOverGround - radius < SPEED_OVER_GROUND_RANGE[0])
//            ? SPEED_OVER_GROUND_RANGE[0]
//            : lastSpeedOverGround - radius;
//
//    Double max =
//        (lastSpeedOverGround + radius) > SPEED_OVER_GROUND_RANGE[1]
//            ? SPEED_OVER_GROUND_RANGE[1]
//            : lastSpeedOverGround + radius;
//
//    return new Double[]{min, max};
//  }
//}
