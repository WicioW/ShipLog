package com.wicio.shiplog.log.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CourseOverGroundTest {

  @Test
  void degreesShouldBeBetweenZeroAndThreeHundredSixty() {
    assertThat(new CourseOverGround(0).getValue()).isBetween(0, 360);
    assertThat(new CourseOverGround(10).getValue()).isBetween(0, 360);
    assertThat(new CourseOverGround(-500).getValue()).isBetween(0, 360);
    assertThat(new CourseOverGround(1000).getValue()).isBetween(0, 360);
  }

  @Test
  void degreesShouldBeNormalized() {
    assertThat(new CourseOverGround(0).getValue()).isEqualTo(0);
    assertThat(new CourseOverGround(10).getValue()).isEqualTo(10);
    assertThat(new CourseOverGround(-500).getValue()).isEqualTo(220);
    assertThat(new CourseOverGround(1000).getValue()).isEqualTo(280);
  }

}