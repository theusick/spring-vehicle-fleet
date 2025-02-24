package com.theusick.fleet.service.mapper;

import com.theusick.fleet.controller.dto.vehicletelemetry.VehicleTelemetryBaseDTO;
import com.theusick.fleet.controller.dto.vehicletelemetry.VehicleTelemetryFeature;
import com.theusick.fleet.repository.entity.VehicleTelemetryEntity;
import com.theusick.fleet.service.mapper.util.MappingTimeUtil;
import com.theusick.fleet.service.model.VehicleTelemetryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Map;

@Mapper(
    componentModel = "spring",
    imports = {
        MappingTimeUtil.class
    }
)
public interface VehicleTelemetryMapper {

    VehicleTelemetryBaseDTO telemetryBaseDTOFromModel(VehicleTelemetryModel model);

    @Mapping(
        target = "timestamp",
        expression = "java(MappingTimeUtil.convertInstantToTimezone(entity.getTimestamp(), " +
            "entity.getVehicle().getEnterprise().getTimezone()))"
    )
    VehicleTelemetryModel telemetryModelFromEntity(VehicleTelemetryEntity entity);

    @Mapping(target = "geometry", source = "coordinates")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "properties", source = "model", qualifiedByName = "mapProperties")
    VehicleTelemetryFeature telemetryModelToFeature(VehicleTelemetryModel model);

    @Named("mapProperties")
    default Map<String, Object> mapProperties(VehicleTelemetryModel model) {
        return Map.of(
            "timestamp", model.getTimestamp()
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(
        target = "timestamp",
        expression = "java(model.getTimestamp().toInstant())"
    )
    void updateVehicleTelemetryEntityFromModel(@MappingTarget VehicleTelemetryEntity entity,
                                               VehicleTelemetryModel model);

}
