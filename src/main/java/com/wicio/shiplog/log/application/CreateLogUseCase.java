package com.wicio.shiplog.log.application;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.api.dto.CreateLogResponse;
import com.wicio.shiplog.log.api.dto.CreateLogResponseMapper;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.services.LogCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateLogUseCase {

  private final LogCreator logCreator;
  private final CreateLogResponseMapper createLogResponseMapper;

  public CreateLogResponse createLog(Long vesselId,
                                     CreateLogRequest createLogRequest) {
    Log log = logCreator.apply(vesselId, createLogRequest);
    return createLogResponseMapper.map(log);
  }
}
