package com.wicio.shiplog.route.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TimeDifferenceCalculator {

  public long minutesBetween(Instant first, Instant second) {
    long minutes = Math.abs(first.until(second, ChronoUnit.MINUTES));
    log.debug(
        "Calculating minutes between: (" + first + ", " + second + "). Difference:" + minutes);
    return minutes;
  }
}
