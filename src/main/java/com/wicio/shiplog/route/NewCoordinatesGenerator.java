package com.wicio.shiplog.route;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import com.wicio.shiplog.log.application.usecase.PointCreator;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NewCoordinatesGenerator {

  private final PointCreator pointCreator;

  /**
   * latitude, longitude - entry point coordinates distanceInMetres - distance that you want to move
   * the point by bearing - an angle, direction towards which you want to move the point. 0 is
   * towards the North, 90 - East, 180 - South, 270 - West. And all between, i.e. 45 is North East.
   * earthRadiusInMetres - Earth radius in metres.
   */
  public Point basedOnDistanceAndBearing(
      double latitude,
      double longitude,
      int distanceInMetres,
      int bearing) {
    double brngRad = toRadians(bearing);
    double latRad = toRadians(latitude);
    double lonRad = toRadians(longitude);
    int earthRadiusInMetres = 6371000;
    double distFrac = (double) distanceInMetres / (double) earthRadiusInMetres;

    double latitudeResult =
        asin(sin(latRad) * cos(distFrac) + cos(latRad) * sin(distFrac) * cos(brngRad));
    double a =
        atan2(
            sin(brngRad) * sin(distFrac) * cos(latRad),
            cos(distFrac) - sin(latRad) * sin(latitudeResult));
    double longitudeResult = (lonRad + a + 3 * PI) % (2 * PI) - PI;

    return pointCreator.apply(
        toDegrees(longitudeResult),
        toDegrees(latitudeResult));
  }
}
