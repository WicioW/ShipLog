package com.wicio.shiplog.route.util;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ClampToRange {

  public int clamp(int value,
                   int min,
                   int max) {
    Assert.isTrue(min <= max, "min must be less than or equal to max");
    return Math.min(Math.max(value, min), max);
  }
}
