package com.wicio.shiplog.log.application.usecase;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class PointCreator {

  private final GeometryFactory geometryFactory = new GeometryFactory();

  public Point apply(double xCoordinate,
                     double yCoordinate) {
    return geometryFactory.createPoint(new Coordinate(xCoordinate, yCoordinate));
  }
}
