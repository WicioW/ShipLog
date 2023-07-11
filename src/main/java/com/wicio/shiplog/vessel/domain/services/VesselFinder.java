package com.wicio.shiplog.vessel.domain.services;

import com.wicio.shiplog.exception.EntityNotFoundException;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VesselFinder {

  private final VesselRepository vesselRepository;

  public Vessel byId(Long id) {
    return vesselRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Vessel.class, id));
  }

  public Vessel referenceById(Long id) {
    return vesselRepository.getReferenceById(id);
  }

}
