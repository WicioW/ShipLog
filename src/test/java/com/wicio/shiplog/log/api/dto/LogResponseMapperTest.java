package com.wicio.shiplog.log.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wicio.shiplog.log.domain.Degree;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.util.RandomPoint;
import com.wicio.shiplog.vessel.domain.Vessel;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class LogResponseMapperTest {

  LogResponseMapper testObj = new LogResponseMapper();

  @Test
  void shouldMapLogToLogResponse() {
    // given
    Vessel vessel = Vessel.builder()
        .build();
    ReflectionTestUtils.setField(vessel, "id", 267L);
    Log log = Log.builder()
        .vessel(vessel)
        .point(RandomPoint.generateRandomPoint())
        .windSpeedInKmPerHour(895)
        .windDirection(new Degree(45))
        .speedOverGroundInKmPerHour(101)
        .courseOverGround(new Degree(90))
        .isStationary(false)
        .build();
    ReflectionTestUtils.setField(log, "id", 764L);
    // when
    var result = testObj.map(log);
    // then
    assertThat(result.id()).isEqualTo(764L);
    assertThat(result.vesselId()).isEqualTo(267L);
    assertThat(result.geoPoint()).isEqualTo(log.getPoint());
    assertThat(result.windSpeedInKmPerHour()).isEqualTo(895);
    assertThat(result.windDirection()).isEqualTo(45);
    assertThat(result.speedOverGroundInKmPerHour()).isEqualTo(101);
    assertThat(result.courseOverGround()).isEqualTo(90);
    assertThat(result.isStationary()).isFalse();

  }
}