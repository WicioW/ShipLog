package com.wicio.shiplog.route.util;

import org.springframework.stereotype.Component;

@Component
public class ClampToRange {

  public int clamp(int value,
                   int min,
                   int max) {
    return Math.min(Math.max(value, min), max);
  }

}
