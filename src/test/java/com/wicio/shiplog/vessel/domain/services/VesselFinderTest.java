package com.wicio.shiplog.vessel.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.exception.EntityNotFoundException;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class VesselFinderTest {

  @InjectMocks
  private VesselFinder testObj;

  @Mock
  private VesselRepository vesselRepository;

  @Test
  void testGetVesselById() {
    // given
    Long id = 758L;
    Vessel vessel = Vessel.builder()
        .build();
    when(vesselRepository.findById(id)).thenReturn(Optional.of(vessel));
    // when
    Vessel result = testObj.byId(id);
    // then
    assertThat(result).isEqualTo(vessel);
  }

  @Test
  void shouldThrowNotFoundException_whenVesselNotFoundInDb() {
    // given
    Long id = 758L;
    when(vesselRepository.findById(id)).thenReturn(Optional.empty());
    // when
    // then
    assertThrows(EntityNotFoundException.class, () -> testObj.byId(id));
  }

  @Test
  void testGetVesselReferenceById() {
    // given
    Long id = 758L;
    Vessel vessel = Vessel.builder()
        .build();
    when(vesselRepository.getReferenceById(id)).thenReturn(vessel);
    // when
    Vessel result = testObj.referenceById(id);
    // then
    assertThat(result).isEqualTo(vessel);
  }

}