package com.theusick.datagenerator.service.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.io.IOException;
import java.util.List;

@Data
@Builder
public class GraphHopperRouteRequest {

    @Size(min = 2, max = 20)
    @JsonSerialize(using = PointsSerializer.class)
    List<Point> points;

    @Builder.Default
    private String profile = "car";
    @Builder.Default
    @JsonProperty("snap_preventions")
    private List<String> snapPreventions = List.of("ford", "ferry");
    @Builder.Default
    private List<String> details = List.of("leg_time", "leg_distance", "time", "distance");
    @Builder.Default
    private boolean instructions = false;
    @Builder.Default
    @JsonProperty("points_encoded")
    private boolean pointsEncoded = false;

    public static class PointsSerializer extends JsonSerializer<List<Point>> {

        @Override
        public void serialize(List<Point> points,
                              JsonGenerator gen,
                              SerializerProvider serializerProvider) throws IOException {
            if (points == null) {
                gen.writeNull();
                return;
            }

            if (points.isEmpty()) {
                gen.writeStartArray();
                gen.writeEndArray();
                return;
            }

            gen.writeStartArray();
            for (Point point : points) {
                gen.writeStartArray();
                gen.writeNumber(point.getY());
                gen.writeNumber(point.getX());
                gen.writeEndArray();
            }
            gen.writeEndArray();
        }
    }

}
