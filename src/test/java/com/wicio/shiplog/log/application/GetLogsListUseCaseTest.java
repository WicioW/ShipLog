package com.wicio.shiplog.log.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.api.dto.LogResponse;
import com.wicio.shiplog.log.api.dto.LogResponseMapper;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.services.LogFinder;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MockitoSettings
class GetLogsListUseCaseTest {

  @InjectMocks
  private GetLogsListUseCase testObj;

  @Mock
  private LogFinder logFinder;
  @Mock
  private LogResponseMapper logResponseMapper;

  @Test
  void testListOfLogsForVessel() {
    //given
    Long vesselId = 1L;
    Pageable pageable = Pageable.unpaged();

    Log log = Log.builder()
        .build();
    Page<Log> logsPage = new PageImpl<>(List.of(log));
    when(logFinder.listOfLogsForVessel(vesselId, pageable)).thenReturn(logsPage);

    LogResponse logResponse = LogResponse.builder()
        .build();
    when(logResponseMapper.map(log)).thenReturn(logResponse);

    //when
    Page<LogResponse> result = testObj.listOfLogsForVessel(vesselId, pageable);
    //then
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent()
        .get(0)).isEqualTo(logResponse);
  }
}