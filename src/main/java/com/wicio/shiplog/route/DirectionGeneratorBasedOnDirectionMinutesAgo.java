package com.wicio.shiplog.route;

import com.wicio.shiplog.route.util.RandomNumberGenerator;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DirectionGeneratorBasedOnDirectionMinutesAgo {

  private final RandomNumberGenerator randomNumberGenerator;

  // To prevent ships from going in circles -> go left
  private static final Double[] IMPOSED_SHIP_COURSE_RANGE = {180d, 360d};

  public Double generateDirection(
      Instant currentTimeStamp,
      Double lastDirection,
      Instant lastTimeStamp,
      long minutesAgo) {
    if (lastDirection == null) {
      return randomNumberGenerator.randomDoubleBetween(IMPOSED_SHIP_COURSE_RANGE[0],
          IMPOSED_SHIP_COURSE_RANGE[1]);
    }
//    long minutesPassed = timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp);
    Double[] range = rangeForImposedCourseRange(minutesAgo, lastDirection);
    log.debug("range:" + range[0] + " " + range[1]);
    Double direction = randomNumberGenerator.randomDoubleBetween(range[0], range[1]);
    log.debug("generated direction:" + direction);
    return direction;
  }

  private Double[] rangeForImposedCourseRange(long minutesPassed,
                                              double lastDirection) {
    long possibleTurnRange = minutesPassed;
    if (possibleTurnRange >= IMPOSED_SHIP_COURSE_RANGE[1] - IMPOSED_SHIP_COURSE_RANGE[0]) {
      return new Double[]{IMPOSED_SHIP_COURSE_RANGE[0], IMPOSED_SHIP_COURSE_RANGE[1]};
    }
    Double max;
    Double min;
    // górna granica
    if (lastDirection + (possibleTurnRange) / 2 > IMPOSED_SHIP_COURSE_RANGE[1]) {
      max = IMPOSED_SHIP_COURSE_RANGE[1];
      min = IMPOSED_SHIP_COURSE_RANGE[1] - possibleTurnRange;
      return new Double[]{min, max};
    }
    // dolna granica
    if (lastDirection - (possibleTurnRange / 2) < IMPOSED_SHIP_COURSE_RANGE[0]) {
      min = IMPOSED_SHIP_COURSE_RANGE[0];
      max = IMPOSED_SHIP_COURSE_RANGE[0] + possibleTurnRange;
      return new Double[]{min, max};
    }

    min = lastDirection - (possibleTurnRange / 2);
    max = lastDirection + (possibleTurnRange / 2);

    return new Double[]{min, max};
  }

  /**
   * range is not between 0 and 360. Meaning values can be smaller than 0 and bigger than 360.
   */
  private Double[] directionRangeForTimeDifference(Double lastDirection,
                                                   long minutes) {
    Double radius = (double) minutes;
    return new Double[]{lastDirection - radius, lastDirection + radius};
  }
}
