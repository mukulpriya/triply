package com.mukul.triply.features.user;

import com.mukul.triply.common.baseclasses.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<UserEntry, UserEntity> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}