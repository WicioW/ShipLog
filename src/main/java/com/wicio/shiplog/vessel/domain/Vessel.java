package com.wicio.shiplog.vessel.domain;

import com.wicio.shiplog.EntitySuperclass;
import com.wicio.shiplog.log.domain.Log;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
public class Vessel extends EntitySuperclass {

  @Column(nullable = false)
  private String name;

  private Instant productionDate;

  @OneToOne private Log lastLog;
}
