package com.wicio.shiplog.vessel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.vessel.api.dto.CreateVesselRequest;
import com.wicio.shiplog.vessel.api.dto.CreateVesselResponse;
import com.wicio.shiplog.vessel.api.dto.CreateVesselResponseMapper;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.services.VesselCreator;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class CreateVesselUseCaseTest {

  @InjectMocks
  private CreateVesselUseCase testObj;

  @Mock
  private VesselCreator vesselCreator;
  @Mock
  private CreateVesselResponseMapper createVesselResponseMapper;

  @Test
  void shouldCreateVessel() {
    // given
    Instant instant = Instant.now();
    String name = "GCl5ewMI";
    CreateVesselRequest request = CreateVesselRequest.builder()
        .name(name)
        .productionDate(instant)
        .build();

    Vessel vessel = Vessel.builder()
        .build();
    when(vesselCreator.apply(name, instant)).thenReturn(vessel);
    CreateVesselResponse response = CreateVesselResponse.builder()
        .build();
    when(createVesselResponseMapper.map(vessel)).thenReturn(response);
    // when
    var result = testObj.createVessel(request);
    // then
    assertThat(result).isEqualTo(response);
  }

}