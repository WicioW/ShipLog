package com.wicio.shiplog.log.domain.services;

import static com.wicio.shiplog.util.RandomObjectUtil.getRandomCreateLogRequest;
import static com.wicio.shiplog.util.RandomPoint.generateRandomPoint;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.api.dto.CreateLogRequest;
import com.wicio.shiplog.log.domain.Log;
import com.wicio.shiplog.log.domain.LogRepository;
import com.wicio.shiplog.vessel.domain.Vessel;
import com.wicio.shiplog.vessel.domain.services.VesselFinder;
import com.wicio.shiplog.vessel.domain.services.VesselUpdater;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class LogCreatorTest {

  @Mock
  private LogRepository logRepository;
  @Mock
  private PointCreator pointCreator;
  @Mock
  private VesselUpdater vesselUpdater;
  @Mock
  private VesselFinder vesselFinder;

  @InjectMocks
  private LogCreator testObj;

  @Test
  void shouldCreateLog_whenProvidingVessel() {
    // given
    CreateLogRequest createLogRequest = getRandomCreateLogRequest();

    Vessel vessel = Vessel.builder()
        .build();
    Point point = generateRandomPoint();
    when(pointCreator.apply(createLogRequest.XCoordinate(), createLogRequest.YCoordinate()))
        .thenReturn(point);

    when(logRepository.save(any(Log.class))).thenAnswer(i -> i.getArguments()[0]);

    // when
    Log result = testObj.apply(vessel, createLogRequest);
    // then
    verify(vesselUpdater).updateLastLog(vessel, result);
    assertThat(result.getVessel())
        .isEqualTo(vessel);
    assertThat(result.getPoint())
        .isEqualTo(point);
    assertThat(result.getSpeedOverGroundInKmPerHour())
        .isEqualTo(createLogRequest.speedOverGround());
    assertThat(result.getCourseOverGround()
        .getValue())
        .isEqualTo(normalizeIntToDegreesBounds(createLogRequest.courseOverGround()));
    assertThat(result.getWindDirection()
        .getValue())
        .isEqualTo(createLogRequest.windDirection());
    assertThat(result.getWindSpeedInKmPerHour())
        .isEqualTo(createLogRequest.windSpeed());
    assertThat(result.isStationary())
        .isEqualTo(createLogRequest.stationary()
            .booleanValue());
  }

  @Test
  void shouldCreateLog_whenProvidingVesselId() {
    // given
    CreateLogRequest createLogRequest = getRandomCreateLogRequest();

    Vessel vessel = Vessel.builder()
        .build();
    Long vesselId = 348L;
    Point point = generateRandomPoint();
    when(vesselFinder.referenceById(vesselId))
        .thenReturn(vessel);
    when(pointCreator.apply(createLogRequest.XCoordinate(), createLogRequest.YCoordinate()))
        .thenReturn(point);

    when(logRepository.save(any(Log.class))).thenAnswer(i -> i.getArguments()[0]);

    // when
    Log result = testObj.apply(vesselId, createLogRequest);
    // then
    verify(vesselUpdater).updateLastLog(vessel, result);
    assertThat(result.getVessel())
        .isEqualTo(vessel);
    assertThat(result.getPoint())
        .isEqualTo(point);
    assertThat(result.getSpeedOverGroundInKmPerHour())
        .isEqualTo(createLogRequest.speedOverGround());
    assertThat(result.getCourseOverGround()
        .getValue())
        .isEqualTo(normalizeIntToDegreesBounds(createLogRequest.courseOverGround()));
    assertThat(result.getWindDirection()
        .getValue())
        .isEqualTo(createLogRequest.windDirection());
    assertThat(result.getWindSpeedInKmPerHour())
        .isEqualTo(createLogRequest.windSpeed());
    assertThat(result.isStationary())
        .isEqualTo(createLogRequest.stationary()
            .booleanValue());
  }

  private int normalizeIntToDegreesBounds(int value) {
    while (value < 0) {
      value += 360;
    }
    while (value > 360) {
      value -= 360;
    }
    return value;
  }

}
