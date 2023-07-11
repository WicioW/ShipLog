package com.wicio.shiplog.log.domain.services;

import com.wicio.shiplog.exception.EntityNotFoundException;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFinder {

  private final LogRepository logRepository;

  public Log byId(Long id) {
    return logRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Log.class, id));
  }

  public Page<Log> listOfLogsForVessel(Long vesselId,
                                       Pageable pageable) {
    return logRepository.findByVesselId(vesselId, pageable);
  }

}
