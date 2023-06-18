package com.wicio.shiplog.route;

import com.wicio.shiplog.route.util.ClampToRange;
import com.wicio.shiplog.route.util.RandomGaussianNumberGenerator;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import com.wicio.shiplog.route.util.vo.NumberRangeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class SpeedGenerator {

  private final RandomNumberGenerator randomNumberGenerator;
  private final RandomGaussianNumberGenerator randomGaussianNumberGenerator;
  private final ClampToRange clampToRange;

  /**
   * Speed over ground range in km/h
   */
  public int generateSpeedOverGround(
      SpeedGeneratorConfigVO speedGeneratorConfigVO,
      Integer lastSpeedOverGround,
      long minutesAgo
  ) {
    Assert.notNull(speedGeneratorConfigVO, "SpeedGeneratorConfigVO cannot be null");
    if (lastSpeedOverGround == null) {
      return randomNumberGenerator.randomIntBetween(
          speedGeneratorConfigVO.speedRangeMinValue(),
          speedGeneratorConfigVO.speedRangeMaxValue());
    }
    NumberRangeVO range = speedOverGroundRangeForTimeDifference(
        speedGeneratorConfigVO,
        lastSpeedOverGround,
        minutesAgo);
    return (int) randomGaussianNumberGenerator.normalDistribution(
        (Integer) range.min(),
        (Integer) range.max());
  }

  private NumberRangeVO speedOverGroundRangeForTimeDifference(
      SpeedGeneratorConfigVO speedGeneratorConfigVO,
      int lastSpeedOverGround,
      long minutes) {
    int possibleDifferenceRange = getPossibleDifferenceRange(minutes, speedGeneratorConfigVO);
    int minAfterTime = lastSpeedOverGround - possibleDifferenceRange;
    int maxAfterTime = lastSpeedOverGround + possibleDifferenceRange;
    return new NumberRangeVO(
        clampToSOGRange(minAfterTime, speedGeneratorConfigVO),
        clampToSOGRange(maxAfterTime, speedGeneratorConfigVO));
  }

  private int clampToSOGRange(int value,
                              SpeedGeneratorConfigVO speedGeneratorConfigVO) {
    return clampToRange.clamp(
        value, speedGeneratorConfigVO.speedRangeMinValue(),
        speedGeneratorConfigVO.speedRangeMaxValue());
  }

  private int getPossibleDifferenceRange(long minutes,
                                         SpeedGeneratorConfigVO speedGeneratorConfigVO) {
    return (int) ((speedGeneratorConfigVO.maximalSpeedChangePerHour() * minutes) / 120);
  }
}
