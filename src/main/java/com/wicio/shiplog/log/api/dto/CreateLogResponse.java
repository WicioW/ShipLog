package com.wicio.shiplog.log.api.dto;

import lombok.Builder;

@Builder
public record CreateLogResponse(
    Long id,
    Long vesselId
) {

}
