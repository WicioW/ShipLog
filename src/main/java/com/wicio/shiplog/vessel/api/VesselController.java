package com.wicio.shiplog.vessel.api;

import com.wicio.shiplog.vessel.api.dto.CreateVesselRequest;
import com.wicio.shiplog.vessel.api.dto.CreateVesselResponse;
import com.wicio.shiplog.vessel.api.dto.VesselResponse;
import com.wicio.shiplog.vessel.application.CreateVesselUseCase;
import com.wicio.shiplog.vessel.application.GetVesselUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vessels")
class VesselController {

  private final GetVesselUseCase getVesselUseCase;
  private final CreateVesselUseCase createVesselUseCase;

  @GetMapping("/{vesselId}")
  VesselResponse getVessel(@PathVariable("vesselId") Long vesselId) {
    return getVesselUseCase.getVesselById(vesselId);
  }

  @PostMapping
  CreateVesselResponse createVessel(@RequestBody @Valid CreateVesselRequest createVesselRequest) {
    return createVesselUseCase.createVessel(createVesselRequest);
  }

}
