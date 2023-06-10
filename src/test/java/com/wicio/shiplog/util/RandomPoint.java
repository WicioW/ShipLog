package com.wicio.shiplog.util;

import org.jeasy.random.EasyRandom;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class RandomPoint {

  private static final EasyRandom easyRandom = new EasyRandom();
  private static final GeometryFactory geometryFactory = new GeometryFactory();

  public static Point generateRandomPoint() {
    return geometryFactory.createPoint(
        new Coordinate(easyRandom.nextDouble(), easyRandom.nextDouble()));
  }
}
