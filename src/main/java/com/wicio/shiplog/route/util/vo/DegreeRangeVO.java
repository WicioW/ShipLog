package com.wicio.shiplog.route.util.vo;

import com.wicio.shiplog.log.domain.Degree;

public record DegreeRangeVO(
    Degree min,
    Degree max
) {

}
