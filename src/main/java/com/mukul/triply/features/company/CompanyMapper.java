package com.mukul.triply.features.company;

import com.mukul.triply.common.baseclasses.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper extends BaseMapper<CompanyEntry, CompanyEntity> {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);
}