package com.theusick.datagenerator.service.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.locationtech.jts.geom.LineString;

import java.util.List;

@Data
public class GraphHopperRouteResponse {

    private List<Paths> paths;

    @Data
    public static class Paths {

        private Double distance;
        private Double weight;
        private Long time;

        private List<Double> bbox;

        private LineString points;
        private Details details;

    }

    @Data
    public static class Details {
        private List<DetailSegment> distance;
        private List<DetailSegment> time;
    }

    @Data
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    public static class DetailSegment {
        private int from;
        private int to;
        private double value;

        @JsonCreator
        public DetailSegment(List<Object> data) {
            this.from = ((Number) data.get(0)).intValue();
            this.to = ((Number) data.get(1)).intValue();
            this.value = ((Number) data.get(2)).doubleValue();
        }
    }

}
