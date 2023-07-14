package com.wicio.shiplog.log.api;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.api.dto.CreateLogResponse;
import com.wicio.shiplog.log.api.dto.LogResponse;
import com.wicio.shiplog.log.application.CreateLogUseCase;
import com.wicio.shiplog.log.application.GetLogsListUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
class LogController {

  private final GetLogsListUseCase getLogsListUseCase;
  private final CreateLogUseCase createLogUseCase;

  @GetMapping("/{vesselId}")
  Page<LogResponse> list(@PathVariable("vesselId") Long vesselId,
                         Pageable pageable) {
    return getLogsListUseCase.listOfLogsForVessel(vesselId, pageable);
  }

  @PostMapping("/{vesselId}")
  CreateLogResponse createLog(@PathVariable("vesselId") Long vesselId,
                              @RequestBody @Valid CreateLogRequest createLogRequest) {
    return createLogUseCase.createLog(vesselId, createLogRequest);
  }
}
