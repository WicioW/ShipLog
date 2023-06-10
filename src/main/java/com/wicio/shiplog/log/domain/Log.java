package com.wicio.shiplog.log.domain;

import com.wicio.shiplog.EntitySuperclass;
import com.wicio.shiplog.vessel.domain.Vessel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.awt.*;

@Getter
@Entity
public class Log extends EntitySuperclass {

    @ManyToOne(optional = false)
    private Vessel vessel;

    private Point point;

}
