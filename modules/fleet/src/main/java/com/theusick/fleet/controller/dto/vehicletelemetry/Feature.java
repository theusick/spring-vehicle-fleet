package com.theusick.fleet.controller.dto.vehicletelemetry;

import org.locationtech.jts.geom.Point;

import java.util.Map;

public interface Feature {

    String TYPE = "Feature";

    Point getGeometry();

    Long getId();

    Map<String, Object> getProperties();

    default String getType() {
        return TYPE;
    }

}
