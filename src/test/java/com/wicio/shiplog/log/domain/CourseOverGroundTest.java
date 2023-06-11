package com.wicio.shiplog.log.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CourseOverGroundTest {

  @Test
  void degreesShouldBeBetweenZeroAndThreeHundredSixty() {
    assertThat(new Degree(0).getValue()).isBetween(0, 360);
    assertThat(new Degree(10).getValue()).isBetween(0, 360);
    assertThat(new Degree(-500).getValue()).isBetween(0, 360);
    assertThat(new Degree(1000).getValue()).isBetween(0, 360);
  }

  @Test
  void degreesShouldBeNormalized() {
    assertThat(new Degree(0).getValue()).isEqualTo(0);
    assertThat(new Degree(10).getValue()).isEqualTo(10);
    assertThat(new Degree(-500).getValue()).isEqualTo(220);
    assertThat(new Degree(1000).getValue()).isEqualTo(280);
  }

}