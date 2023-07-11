package com.wicio.shiplog.log.application;

import com.wicio.shiplog.log.api.dto.LogResponse;
import com.wicio.shiplog.log.api.dto.LogResponseMapper;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.services.LogFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetLogsListUseCase {

  private final LogFinder logFinder;
  private final LogResponseMapper logResponseMapper;

  public Page<LogResponse> listOfLogsForVessel(Long vesselId,
                                               Pageable pageable) {
    Page<Log> logs = logFinder.listOfLogsForVessel(vesselId, pageable);
    return new PageImpl<>(
        logs.getContent()
            .stream()
            .map(logResponseMapper::map)
            .toList(),
        pageable,
        logs.getTotalElements());
  }

}
