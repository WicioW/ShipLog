package com.wicio.shiplog.route;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

import com.wicio.shiplog.log.application.usecase.PointCreator;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class NewCoordinatesGeneratorTest {

  @InjectMocks
  private NewCoordinatesGenerator testObj;

  @Mock
  private PointCreator pointCreator;

  //  X - > long Y -> lat
  @Test
  void testNewCoordinatesGeneration() {
    //given
    double latitude = 37.7749;
    double longitude = -122.4194;
    int distanceInMetres = 300_000; //300 km
    int bearing = 45;

    when(pointCreator.apply(anyDouble(), anyDouble())).thenAnswer(i ->
        new GeometryFactory().createPoint(
            new Coordinate((Double) i.getArguments()[0], (Double) i.getArguments()[1])));

    //when
    Point result = testObj.basedOnDistanceAndBearing(latitude, longitude, distanceInMetres,
        bearing);
    //then
    assertThat(result.getX()).isEqualTo(-119.941, offset(0.01));
    assertThat(result.getY()).isEqualTo(39.657, offset(0.01));
  }
}