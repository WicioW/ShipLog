package com.wicio.shiplog.vessel.domain;

import com.wicio.shiplog.EntitySuperclass;
import com.wicio.shiplog.log.domain.Log;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vessel extends EntitySuperclass {

  @Column(nullable = false)
  private String name;

  private Instant productionDate;

  @OneToOne
  @JoinColumn(name = "last_log_id", referencedColumnName = "id")
  private Log lastLog;

  public void updateLastLog(Log log) {
    Assert.notNull(log, "Log cannot be null");
    Assert.isTrue(log.getVessel() == this, "Log must be for this vessel");
    this.lastLog = log;
  }
}
