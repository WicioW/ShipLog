package com.wicio.shiplog.log.api.dto;

import com.wicio.shiplog.log.domain.Log;
import org.springframework.stereotype.Component;

@Component
public class CreateLogResponseMapper {

  public CreateLogResponse map(Log log) {
    return CreateLogResponse.builder()
        .id(log.getId())
        .vesselId(log.getVessel()
            .getId())
        .build();
  }
}
