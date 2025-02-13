package com.theusick.fleet.controller.dto.vehicletelemetry;

import java.util.List;

public interface FeatureCollection<F extends Feature> {

    String TYPE = "FeatureCollection";

    List<F> getFeatures();

    default String getType() {
        return TYPE;
    }

}
