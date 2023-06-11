package com.wicio.shiplog.log.domain;

import com.wicio.shiplog.EntitySuperclass;
import com.wicio.shiplog.vessel.domain.Vessel;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;
import org.springframework.lang.Nullable;

@Getter
@Builder
@Entity
public class Log extends EntitySuperclass {

  @ManyToOne(optional = false)
  private Vessel vessel;

  /**
   * X - > long Y -> lat
   */
  private Point point;

  @Nullable
  private Double speedOverGroundInKmPerHour;
  @Nullable
  @Embedded
  private CourseOverGround courseOverGround;
  @Nullable
  private Double windDirection;
  @Nullable
  private Double windSpeedInKmPerHour;
  @Builder.Default
  private boolean isStationary = true;
}
