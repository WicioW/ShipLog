package com.wicio.shiplog.route.util.dto;

import com.wicio.shiplog.log.domain.Degree;

public record DegreeRangeDTO(
    Degree min,
    Degree max
) {

}
