package com.mukul.triply.common.baseclasses;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@MapperConfig(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMapper<T extends BaseEntry, U extends BaseEntity> {

    @Mapping(ignore = true, source = "id", target = "id")
    U toEntity(T entry);

    List<U> toEntities(List<T> entries);

    T toEntry(U entity);

    List<T> toEntries(List<U> entities);

    @Mapping(ignore = true, source = "id", target = "id")
    @Mapping(ignore = true, source = "createdBy", target = "createdBy")
    @Mapping(ignore = true, source = "createdAt", target = "createdAt")
    @Mapping(ignore = true, source = "version", target = "version")
    U toUpdate(U request, @MappingTarget U target);
}
