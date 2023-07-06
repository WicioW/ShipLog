package com.wicio.shiplog.route.util;

import java.util.Random;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RandomNumberGenerator {

  private static final Random random = new Random();

  public int randomIntBetween(int min,
                              int max) {
    Assert.isTrue(min <= max, "min must be less than or equal to max");
    return random.nextInt(max - min + 1) + min;
  }

  public double randomDoubleBetween(double min,
                                    double max) {
    Assert.isTrue(min <= max, "min must be less than or equal to max");
    return min + (max - min) * random.nextDouble();
  }
}
