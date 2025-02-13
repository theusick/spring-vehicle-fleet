package com.theusick.fleet.controller.dto.vehicletelemetry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.time.OffsetDateTime;

@Value
@Builder
@Jacksonized
@Schema(name = "VehicleTelemetry")
public class VehicleTelemetryBaseDTO {

    Long id;

    OffsetDateTime timestamp;
    @JsonSerialize(using = PointCoordinatesSerializer.class)
    Point coordinates;

    public static class PointCoordinatesSerializer extends JsonSerializer<Point> {

        @Override
        public void serialize(Point point,
                              JsonGenerator gen,
                              SerializerProvider serializerProvider) throws IOException {
            if (point == null) {
                gen.writeNull();
                return;
            }

            gen.writeStartObject();
            gen.writeNumberField("lat", point.getY());
            gen.writeNumberField("lon", point.getX());
            gen.writeEndObject();
        }
    }

}
