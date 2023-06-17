package com.wicio.shiplog.route;

import com.wicio.shiplog.route.util.ClampToRange;
import com.wicio.shiplog.route.util.RandomGaussianNumberGenerator;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import com.wicio.shiplog.route.util.vo.NumberRangeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeedOverGroundGenerator {

  private final RandomNumberGenerator randomNumberGenerator;
  private final RandomGaussianNumberGenerator randomGaussianNumberGenerator;
  private final ClampToRange clampToRange;

  /**
   * Speed over ground range in km/h
   */
  private static final int SPEED_OVER_GROUND_MIN = 0;
  private static final int SPEED_OVER_GROUND_MAX = 20;

  private static final int MAXIMAL_SPEED_OVER_GROUND_CHANGE_PER_HOUR = 20;

  public int generateSpeedOverGround(
      Integer lastSpeedOverGround,
      long minutesAgo
  ) {
    if (lastSpeedOverGround == null) {
      return randomNumberGenerator.randomIntBetween(SPEED_OVER_GROUND_MIN,
          SPEED_OVER_GROUND_MAX);
    }

//    long minutesAgo = timeDifferenceCalculator.minutesBetween(currentTimeStamp, lastTimeStamp);
//    log.debug("sog, minutes passed:" + minutesAgo);
    NumberRangeVO range = speedOverGroundRangeForTimeDifference(lastSpeedOverGround, minutesAgo);
//    log.debug("sog:" + range[0] + " " + range[1]);
    return (int) randomGaussianNumberGenerator.randomGaussian(
        (Integer) range.min(),
        (Integer) range.max());
  }

  private NumberRangeVO speedOverGroundRangeForTimeDifference(int lastSpeedOverGround,
                                                              long minutes) {
    int possibleDifferenceRange = getPossibleDifferenceRange(minutes);

    int minAfterTime = lastSpeedOverGround - possibleDifferenceRange;
//    int min =
//        (minAfterTime < SPEED_OVER_GROUND_MIN)
//            ? SPEED_OVER_GROUND_MIN
//            : minAfterTime;

    int maxAfterTime = lastSpeedOverGround + possibleDifferenceRange;
//    int max =
//        maxAfterTime > SPEED_OVER_GROUND_MAX
//            ? SPEED_OVER_GROUND_MAX
//            : maxAfterTime;

//    return new Double[]{min, max};
    return new NumberRangeVO(
        clampToSOGRange(minAfterTime),
        clampToSOGRange(maxAfterTime));

//        min, max);
  }

  private int clampToSOGRange(int value) {
    return clampToRange.clamp(value, SPEED_OVER_GROUND_MIN, SPEED_OVER_GROUND_MAX);
  }

  private int getPossibleDifferenceRange(long minutes) {
    return (int) ((MAXIMAL_SPEED_OVER_GROUND_CHANGE_PER_HOUR * minutes) / 120);
  }
}
