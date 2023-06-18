package com.wicio.shiplog.route;

public record SpeedGeneratorConfigVO(
    int speedRangeMinValue,
    int speedRangeMaxValue,
    int maximalSpeedChangePerHour
) {

}
