package com.wicio.shiplog.route;

import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.route.util.RandomNumberGenerator;
import com.wicio.shiplog.route.util.vo.DegreeRangeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectionGeneratorBasedOnDirectionMinutesAgo {

  private final RandomNumberGenerator randomNumberGenerator;

  public Degree generateDirection(
      Degree lastDirection,
      long minutesAgo) {
    int returnValue;
    if (lastDirection == null) {
      returnValue = randomNumberGenerator.randomIntBetween(0, 360);
    } else {
      DegreeRangeVO range = rangeForImposedDirectionRange(minutesAgo, lastDirection.getValue());
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

  private DegreeRangeVO rangeForImposedDirectionRange(long minutesPassed,
                                                      int lastDirection) {
    int possibleTurnValue = possibleVesselTurnValue(minutesPassed);
    int maxValueAfterTime = lastDirection + possibleTurnValue;
    int minValueAfterTime = lastDirection - possibleTurnValue;
    return new DegreeRangeVO(
        new Degree(clampToDegreeRange(minValueAfterTime)),
        new Degree(clampToDegreeRange(maxValueAfterTime)));
  }

  // arbitrary method of calculating turn value over time
  private int possibleVesselTurnValue(long minutesPassed) {
    return (int) (minutesPassed / 2);
  }

  private int clampToDegreeRange(int value) {
    return Math.min(Math.max(value, 0), 360);
  }

}
