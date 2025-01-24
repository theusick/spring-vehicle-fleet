package com.theusick.frontend.controller.mapper;

import com.theusick.frontend.controller.dto.EnterpriseViewDTO;
import com.theusick.fleet.service.model.EnterpriseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnterpriseViewMapper {

    EnterpriseViewDTO enterpriseViewDTOFromModel(EnterpriseModel enterpriseModel);

}
