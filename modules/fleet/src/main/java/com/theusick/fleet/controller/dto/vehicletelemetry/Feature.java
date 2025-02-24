package com.theusick.fleet.controller.dto.vehicletelemetry;

import org.locationtech.jts.geom.Geometry;

import java.util.Map;

public interface Feature {

    String TYPE = "Feature";

    Geometry getGeometry();

    Long getId();

    Map<String, Object> getProperties();

    default String getType() {
        return TYPE;
    }

}
