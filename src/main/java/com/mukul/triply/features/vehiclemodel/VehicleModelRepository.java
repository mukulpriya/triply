package com.mukul.triply.features.vehiclemodel;

import com.mukul.triply.common.baseclasses.BaseRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Optional;

@Repository
public interface VehicleModelRepository extends BaseRepository<VehicleModelEntity> {
    Optional<VehicleModelEntity> findByNameAndBrandAndTypeAndMake(final String name, final String brand, final VehicleType type, final Year make);
}
