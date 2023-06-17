package com.wicio.shiplog.route.util;

import java.util.Random;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RandomGaussianNumberGenerator {

  public double normalDistribution(int min,
                                   int max) {
    Assert.isTrue(min <= max, "min must be less than or equal to max");
    Random r = new Random();
    double mean = (min + max) / 2.0;
    double stdDev = (max - mean) / 3.0;
    return r.nextGaussian() * stdDev + mean;
  }

}
