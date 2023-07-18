package com.wicio.shiplog.route.util.dto;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;

public record NewVesselLogDTO(
    Long vesselId,
    CreateLogRequest createLogRequest
) {

}
