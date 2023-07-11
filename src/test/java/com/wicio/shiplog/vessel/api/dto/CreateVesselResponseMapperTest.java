package com.wicio.shiplog.vessel.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wicio.shiplog.vessel.domain.Vessel;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class CreateVesselResponseMapperTest {

  CreateVesselResponseMapper testObj = new CreateVesselResponseMapper();

  @Test
  void shouldMapVesselToCreateVesselResponse() {
    // given
    Vessel vessel = Vessel.builder()
        .productionDate(Instant.parse("2020-01-01T00:00:00.00Z"))
        .name("4a3fRGA")
        .build();
    ReflectionTestUtils.setField(vessel, "id", 267L);
    // when
    var result = testObj.map(vessel);
    // then
    assertThat(result.id()).isEqualTo(267L);
    assertThat(result.productionDate()).isEqualTo(Instant.parse("2020-01-01T00:00:00.00Z"));
    assertThat(result.name()).isEqualTo("4a3fRGA");
  }
}