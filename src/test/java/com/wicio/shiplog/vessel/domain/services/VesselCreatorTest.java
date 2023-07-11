package com.wicio.shiplog.vessel.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class VesselCreatorTest {

  @InjectMocks
  private VesselCreator testObj;

  @Mock
  private VesselRepository vesselRepository;

  @Test
  void testVesselCreation() {
    //given
    String name = "74vrFm";
    Instant instant = Instant.now();

    when(vesselRepository.save(any(Vessel.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    //when
    Vessel result = testObj.apply(name, instant);
    //then
    assertThat(result.getName()).isEqualTo(name);
    assertThat(result.getProductionDate()).isEqualTo(instant);
  }
}