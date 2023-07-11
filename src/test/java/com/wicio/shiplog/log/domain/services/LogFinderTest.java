package com.wicio.shiplog.log.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.exception.EntityNotFoundException;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.LogRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


@MockitoSettings
class LogFinderTest {

  @InjectMocks
  private LogFinder testObj;

  @Mock
  private LogRepository logRepository;

  @Test
  void shouldFindLogById() {
    // given
    Long id = 709L;
    Log log = Log.builder()
        .build();
    when(logRepository.findById(id)).thenReturn(Optional.of(log));
    // when
    Log result = testObj.byId(id);
    // then
    assertThat(result).isEqualTo(log);
  }

  @Test
  void shouldThrowNotFoundException_whenLogNotInRepository() {
    // given
    Long id = 709L;
    when(logRepository.findById(id)).thenReturn(Optional.empty());
    // when
    // then
    assertThrows(EntityNotFoundException.class, () -> testObj.byId(id));
  }

  @Test
  void shouldFindLogsByVesselId() {
    // given
    Long vesselId = 709L;
    Pageable pageable = Pageable.unpaged();
    Log log = Log.builder()
        .build();
    PageImpl<Log> logsPage = new PageImpl<>(List.of(log));
    when(logRepository.findByVesselId(vesselId, pageable)).thenReturn(logsPage);
    // when
    Page<Log> result = testObj.listOfLogsForVessel(vesselId, pageable);
    // then
    assertThat(result).isEqualTo(logsPage);
  }
}