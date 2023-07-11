package com.wicio.shiplog.log.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.vessel.domain.Vessel;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class CreateLogResponseMapperTest {

  CreateLogResponseMapper testObj = new CreateLogResponseMapper();

  @Test
  void shouldMapLogToCreateLogResponse() {
    // given
    Vessel vessel = Vessel.builder()
        .build();
    ReflectionTestUtils.setField(vessel, "id", 267L);
    Log log = Log.builder()
        .vessel(vessel)
        .build();
    ReflectionTestUtils.setField(log, "id", 764L);
    // when
    var result = testObj.map(log);
    // then
    assertThat(result.id()).isEqualTo(764L);
    assertThat(result.vesselId()).isEqualTo(267L);
  }

}