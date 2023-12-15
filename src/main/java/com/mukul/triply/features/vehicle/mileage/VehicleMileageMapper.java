package com.mukul.triply.features.vehicle.mileage;

import com.mukul.triply.common.baseclasses.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleMileageMapper extends BaseMapper<VehicleMileageEntry, VehicleMileageEntity> {
    VehicleMileageMapper INSTANCE = Mappers.getMapper(VehicleMileageMapper.class);
}