package com.wicio.shiplog.util;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import org.jeasy.random.EasyRandom;

public class RandomObjectUtil {

  private static final EasyRandom easyRandom = new EasyRandom();

  public static CreateLogRequest getRandomCreateLogRequest() {
    return CreateLogRequest.builder()
        .YCoordinate(randomDouble())
        .XCoordinate(randomDouble())
        .speedOverGround(randomInteger())
        .courseOverGround(randomInteger())
        .windDirection(randomInteger())
        .windSpeed(randomInteger())
        .stationary(easyRandom.nextObject(Boolean.class))
        .build();
  }

  private static Integer randomInteger() {
    return easyRandom.nextInt(0, 100);
  }

  private static Double randomDouble() {
    return easyRandom.nextObject(Double.class);
  }
}
