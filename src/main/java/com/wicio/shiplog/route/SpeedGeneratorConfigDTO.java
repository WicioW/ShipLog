package com.wicio.shiplog.route;

record SpeedGeneratorConfigDTO(
    int speedRangeMinValue,
    int speedRangeMaxValue,
    int maximalSpeedChangePerHour
) {

}
