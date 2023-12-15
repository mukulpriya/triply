package com.mukul.triply.features.vehicle;

import com.mukul.triply.common.baseclasses.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleMapper extends BaseMapper<VehicleEntry, VehicleEntity> {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);
}