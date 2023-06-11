package com.wicio.shiplog.route.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.List;

public class RouteInitialPoint {

    GeometryFactory geometryFactory = new GeometryFactory();

    private static final String COORDINATE_IRELAND = "52.073465, -10.67018";
    private static final String COORDINATE_PORTUGAL = "39.451387, -31.269986";
    private static final String COORDINATE_CAPE_VERDE = "14.836941, -24.750791";
    private static final String COORDINATE_GALAPAGOS = "-0.318433, -91.667156";
    private static final String COORDINATE_HAWAII = "21.652695, -160.548538";
    private static final String COORDINATE_AUSTRALIA = "-25.559998, 112.847445";
    private static final String COORDINATE_SOUTH_AFRICA = "-33.870269, 17.655306";

    public static final List<String> COORDINATES_LIST = List.of(
            COORDINATE_IRELAND,
            COORDINATE_PORTUGAL,
            COORDINATE_CAPE_VERDE,
            COORDINATE_GALAPAGOS,
            COORDINATE_HAWAII,
            COORDINATE_AUSTRALIA,
            COORDINATE_SOUTH_AFRICA
    );

    public List<Point> pointsList() {
        return COORDINATES_LIST.stream()
                .map(s -> {
                    String[] coords = s.replace(" ", "")
                            .split(",");
                    return geometryFactory
                            .createPoint(
                                    new Coordinate(
                                            Double.parseDouble(coords[1]),
                                            Double.parseDouble(coords[0])));
                })
                .toList();
    }
}
