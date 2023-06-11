package com.wicio.shiplog.log.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class CourseOverGround {

  private int value;

  /**
   * @param value Value is in degrees between 0 and 360. 0 is towards the North, 90 - East, 180 -
   *              South, 270 - West. And all between, i.e. 45 is North East
   */
  public CourseOverGround(int value) {
    this.value = normalizedCog(value);
  }

  private int normalizedCog(int direction) {
    while (direction < 0) {
      direction += 360;
    }
    while (direction > 360) {
      direction -= 360;
    }
    return direction;
  }

}
