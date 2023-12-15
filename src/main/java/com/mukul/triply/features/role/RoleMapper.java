package com.mukul.triply.features.role;

import com.mukul.triply.common.baseclasses.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleEntry, RoleEntity> {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
}