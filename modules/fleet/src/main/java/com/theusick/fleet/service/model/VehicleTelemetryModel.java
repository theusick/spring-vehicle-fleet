package com.theusick.fleet.service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTelemetryModel {

    private Long id;

    private OffsetDateTime timestamp;
    private Point coordinates;

}
