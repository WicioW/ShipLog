package com.wicio.shiplog;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;

@Getter
@MappedSuperclass
public class EntitySuperclass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Instant created = Instant.now();

    public String toLogString() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName + "[id=" + id+"] ";
    }
}
