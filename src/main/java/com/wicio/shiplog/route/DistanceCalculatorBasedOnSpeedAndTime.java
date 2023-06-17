package com.wicio.shiplog.route;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class DistanceCalculatorBasedOnSpeedAndTime {

  public double distanceInMetres(
      Integer speedOverGroundInKmPerH,
      long minutes
  ) {
    Assert.state(speedOverGroundInKmPerH >= 0, "Speed cannot be negative");
    Assert.state(minutes >= 0, "Time cannot be negative");

    BigDecimal speedOverGroundInKmPerHBigDecimal = BigDecimal.valueOf(speedOverGroundInKmPerH);
    BigDecimal speedInMetersPerMin =
        speedOverGroundInKmPerHBigDecimal
            .multiply(
                BigDecimal.valueOf(1000d / 60d));
    BigDecimal distanceInMetres = speedInMetersPerMin.multiply(BigDecimal.valueOf(minutes));
    return distanceInMetres.doubleValue();
  }

  public double distanceInKilometres(
      Integer speedOverGroundInKmPerH,
      long hours
  ) {
    Assert.state(speedOverGroundInKmPerH >= 0, "Speed cannot be negative");
    Assert.state(hours >= 0, "Time cannot be negative");
    return speedOverGroundInKmPerH * hours;
  }

}
