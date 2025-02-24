package com.theusick.fleet.service.mapper;

import com.theusick.fleet.controller.dto.vehicletelemetry.route.VehicleTelemetryRouteFeature;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.repository.entity.VehicleTelemetryEntity;
import com.theusick.fleet.service.mapper.util.MappingTimeUtil;
import com.theusick.fleet.service.model.VehicleTelemetryRouteModel;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Mapper(
    componentModel = "spring",
    imports = {
        MappingTimeUtil.class
    }
)
public interface VehicleTelemetryRouteMapper {

    @Mapping(target = "geometry", source = "route")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "properties", source = "model", qualifiedByName = "mapProperties")
    VehicleTelemetryRouteFeature routeModelToFeature(VehicleTelemetryRouteModel model);

    @Named("mapProperties")
    default Map<String, Object> mapProperties(VehicleTelemetryRouteModel model) {
        return Map.of(
            "vehicleId", model.getVehicleId(),
            "timestampStart", model.getTimestampStart(),
            "timestampEnd", model.getTimestampEnd()
        );
    }

    default VehicleTelemetryRouteModel routeModelFromEntities(List<VehicleTelemetryEntity> telemetryList,
                                                              EnterpriseEntity enterpriseEntity) {
        if ((telemetryList == null) || telemetryList.isEmpty()) {
            return null;
        }
        VehicleTelemetryRouteModel.VehicleTelemetryRouteModelBuilder builder =
            VehicleTelemetryRouteModel.builder();

        builder.vehicleId(telemetryList.getFirst().getId());
        builder.route(buildLineString(telemetryList));

        builder.timestampStart(
            MappingTimeUtil.convertInstantToTimezone(
                getStartTimestamp(telemetryList),
                enterpriseEntity.getTimezone()));
        builder.timestampEnd(MappingTimeUtil.convertInstantToTimezone(
            getEndTimestamp(telemetryList),
            enterpriseEntity.getTimezone()));

        return builder.build();
    }

    default LineString buildLineString(List<VehicleTelemetryEntity> telemetryList) {
        List<VehicleTelemetryEntity> sorted = telemetryList.stream()
            .sorted(Comparator.comparing(VehicleTelemetryEntity::getTimestamp))
            .toList();
        Coordinate[] coordinates = sorted.stream()
            .map(entity -> entity.getCoordinates().getCoordinate())
            .toArray(Coordinate[]::new);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), VehicleTelemetryEntity.SRID_WGS84);
        return geometryFactory.createLineString(coordinates);
    }

    default Instant getStartTimestamp(List<VehicleTelemetryEntity> telemetryList) {
        return telemetryList.stream()
            .map(VehicleTelemetryEntity::getTimestamp)
            .min(Comparator.naturalOrder())
            .orElse(null);
    }

    default Instant getEndTimestamp(List<VehicleTelemetryEntity> telemetryList) {
        return telemetryList.stream()
            .map(VehicleTelemetryEntity::getTimestamp)
            .max(Comparator.naturalOrder())
            .orElse(null);
    }

}
