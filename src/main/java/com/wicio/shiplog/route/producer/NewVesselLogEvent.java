package com.wicio.shiplog.route.producer;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;

public record NewVesselLogEvent(
    Long vesselId,
    CreateLogRequest createLogRequest
) {

}
