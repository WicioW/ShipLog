package com.wicio.shiplog;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class EntitySuperclass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  public String toLogString() {
    String simpleName = this.getClass()
        .getSimpleName();
    return simpleName + "[id=" + id + "] ";
  }
}
