package com.wicio.shiplog.vessel.api.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.api.dto.LogResponse;
import com.wicio.shiplog.log.api.dto.LogResponseMapper;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.vessel.domain.Vessel;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;

@MockitoSettings
class VesselResponseMapperTest {

  @InjectMocks
  private VesselResponseMapper testObj;

  @Mock
  private LogResponseMapper logResponseMapper;

  @Test
  void shouldMapVesselToVesselResponse() {
    // given
    Log log = Log.builder()
        .build();
    Vessel vessel = Vessel.builder()
        .productionDate(Instant.parse("2022-11-13T12:30:10.00Z"))
        .name("4a3fRGA")
        .lastLog(log)
        .build();
    ReflectionTestUtils.setField(vessel, "id", 180L);
    LogResponse logResponse = LogResponse.builder()
        .build();
    when(logResponseMapper.map(log)).thenReturn(logResponse);

    // when
    var result = testObj.map(vessel);
    // then
    assertThat(result.id()).isEqualTo(180L);
    assertThat(result.productionDate()).isEqualTo(Instant.parse("2022-11-13T12:30:10.00Z"));
    assertThat(result.name()).isEqualTo("4a3fRGA");
    assertThat(result.lastLog()).isEqualTo(logResponse);
  }
}