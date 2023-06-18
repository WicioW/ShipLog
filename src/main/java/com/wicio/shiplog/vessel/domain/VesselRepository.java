package com.wicio.shiplog.vessel.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long> {

  List<Vessel> findAllByLastLogIsNull();

  List<Vessel> findAllByLastLogIsNotNull();

}