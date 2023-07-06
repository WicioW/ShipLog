package com.wicio.shiplog.route;

import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.route.util.ClampToRange;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import com.wicio.shiplog.route.util.dto.DegreeRangeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DirectionGeneratorBasedOnDirectionMinutesAgo {

  private final RandomNumberGenerator randomNumberGenerator;
  private final ClampToRange clampToRange;

  public Degree generateDirection(
      Degree lastDirection,
      long minutesAgo) {
    int returnValue;
    if (lastDirection == null) {
      returnValue = randomNumberGenerator.randomIntBetween(0, 360);
    } else {
      DegreeRangeDTO range = rangeForImposedDirectionRange(minutesAgo, lastDirection.getValue());
      returnValue = randomNumberGenerator.randomIntBetween(
          range.min()
              .getValue(),
          range.max()
              .getValue());
    }
    return letsMakeDirectionPointOnlyToTheLeftSide(new Degree(returnValue));
  }

  /**
   * To prevent ships from going in circles -> go left
   */
  private Degree letsMakeDirectionPointOnlyToTheLeftSide(Degree degree) {
    int cog = degree.getValue();
    if (cog < 180) {
      cog = cog + 180;
    }
    return new Degree(cog);
  }

  private DegreeRangeDTO rangeForImposedDirectionRange(long minutesPassed,
                                                       int lastDirection) {
    int possibleTurnValue = possibleVesselTurnValue(minutesPassed);
    int maxValueAfterTime = lastDirection + possibleTurnValue;
    int minValueAfterTime = lastDirection - possibleTurnValue;
    return new DegreeRangeDTO(
        new Degree(clampToDegreeRange(minValueAfterTime)),
        new Degree(clampToDegreeRange(maxValueAfterTime)));
  }

  // arbitrary method of calculating turn value over time
  private int possibleVesselTurnValue(long minutesPassed) {
    return (int) (minutesPassed / 2);
  }

  private int clampToDegreeRange(int value) {
    return clampToRange.clamp(value, 0, 360);
  }

}
