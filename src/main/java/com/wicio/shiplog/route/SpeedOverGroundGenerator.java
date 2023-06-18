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
    NumberRangeVO range = speedOverGroundRangeForTimeDifference(lastSpeedOverGround, minutesAgo);
    return (int) randomGaussianNumberGenerator.normalDistribution(
        (Integer) range.min(),
        (Integer) range.max());
  }

  private NumberRangeVO speedOverGroundRangeForTimeDifference(int lastSpeedOverGround,
                                                              long minutes) {
    int possibleDifferenceRange = getPossibleDifferenceRange(minutes);
    int minAfterTime = lastSpeedOverGround - possibleDifferenceRange;
    int maxAfterTime = lastSpeedOverGround + possibleDifferenceRange;
    return new NumberRangeVO(
        clampToSOGRange(minAfterTime),
        clampToSOGRange(maxAfterTime));
  }

  private int clampToSOGRange(int value) {
    return clampToRange.clamp(value, SPEED_OVER_GROUND_MIN, SPEED_OVER_GROUND_MAX);
  }

  private int getPossibleDifferenceRange(long minutes) {
    return (int) ((MAXIMAL_SPEED_OVER_GROUND_CHANGE_PER_HOUR * minutes) / 120);
  }
}
