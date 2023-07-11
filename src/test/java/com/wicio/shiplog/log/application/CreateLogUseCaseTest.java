package com.wicio.shiplog.log.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.api.dto.CreateLogResponse;
import com.wicio.shiplog.log.api.dto.CreateLogResponseMapper;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.services.LogCreator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class CreateLogUseCaseTest {

  @InjectMocks
  private CreateLogUseCase testObj;

  @Mock
  private LogCreator logCreator;
  @Mock
  private CreateLogResponseMapper createLogResponseMapper;

  @Test
  void shouldCreateLog() {
    // given
    Long vesselId = 1L;
    CreateLogRequest createLogRequest = CreateLogRequest.builder()
        .build();
    Log log = Log.builder()
        .build();
    when(logCreator.apply(vesselId, createLogRequest)).thenReturn(log);
    CreateLogResponse createLogResponse = CreateLogResponse.builder()
        .build();
    when(createLogResponseMapper.map(log)).thenReturn(createLogResponse);

    // when
    var result = testObj.createLog(vesselId, createLogRequest);
    // then
    assertThat(result).isEqualTo(createLogResponse);
  }

}