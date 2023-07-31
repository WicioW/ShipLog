package com.wicio.shiplog.log.domain;

import com.wicio.shiplog.EntitySuperclass;
import com.wicio.shiplog.vessel.domain.Vessel;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import org.springframework.lang.Nullable;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Log extends EntitySuperclass {

  @ManyToOne(optional = false)
  @JoinColumn(name = "vessel_id", nullable = false)
  private Vessel vessel;

  /**
   * X - > long Y -> lat
   */
  private Point point;

  @Nullable
  private Integer speedOverGroundInKmPerHour;
  @Nullable
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "value", column = @Column(name = "course_over_ground"))
  })
  private Degree courseOverGround;
  @Nullable
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "value", column = @Column(name = "wind_direction"))
  })
  private Degree windDirection;
  @Nullable
  private Integer windSpeedInKmPerHour;
  @Builder.Default
  private boolean isStationary = true;


}
