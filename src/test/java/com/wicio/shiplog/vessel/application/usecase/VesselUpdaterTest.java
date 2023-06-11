package com.wicio.shiplog.vessel.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class VesselUpdaterTest {

  @Mock
  private VesselRepository vesselRepository;
  @InjectMocks
  private VesselUpdater testObj;

  @Test
  void shouldUpdateLastLog() {
    // given
    Vessel vessel = Vessel.builder()
        .build();
    Log log = Log.builder()
        .vessel(vessel)
        .build();

    when(vesselRepository.save(any(Vessel.class))).thenAnswer(i -> i.getArguments()[0]);
    // when
    Vessel result = testObj.updateLastLog(vessel, log);

    // then
    assertThat(result.getLastLog()).isEqualTo(log);
  }
}
