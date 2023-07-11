package com.wicio.shiplog.vessel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.vessel.api.dto.VesselResponse;
import com.wicio.shiplog.vessel.api.dto.VesselResponseMapper;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.services.VesselFinder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class GetVesselUseCaseTest {

  @InjectMocks
  private GetVesselUseCase testObj;

  @Mock
  private VesselFinder vesselFinder;
  @Mock
  private VesselResponseMapper vesselResponseMapper;

  @Test
  void shouldGetVesselById() {
    // given
    Long vesselId = 808L;
    Vessel vessel = Vessel.builder()
        .build();
    when(vesselFinder.byId(vesselId)).thenReturn(vessel);
    VesselResponse response = VesselResponse.builder()
        .build();
    when(vesselResponseMapper.map(vessel)).thenReturn(response);
    // when
    VesselResponse result = testObj.getVesselById(vesselId);
    // then
    assertThat(result).isEqualTo(response);
  }

}