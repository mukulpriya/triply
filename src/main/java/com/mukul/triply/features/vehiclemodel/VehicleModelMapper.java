package com.mukul.triply.features.vehiclemodel;

import com.mukul.triply.common.baseclasses.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleModelMapper extends BaseMapper<VehicleModelEntry, VehicleModelEntity> {
    VehicleModelMapper INSTANCE = Mappers.getMapper(VehicleModelMapper.class);
}