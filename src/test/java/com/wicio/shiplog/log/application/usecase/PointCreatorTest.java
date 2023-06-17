package com.wicio.shiplog.log.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class PointCreatorTest {

  @InjectMocks
  private PointCreator testObj;

  @Test
  void shouldCreatePointFromXAndYCoordinates() {
    // given
    double x = 50.0;
    double y = 65.0;
    // when
    Point result = testObj.apply(x, y);
    // then
    assertThat(result.getX()).isEqualTo(x);
    assertThat(result.getY()).isEqualTo(y);
  }
}
