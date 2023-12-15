package com.mukul.triply.features.vehicle;

import com.mukul.triply.common.baseclasses.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends BaseRepository<VehicleEntity> {
    Optional<VehicleEntity> findByRegistrationNumber(String registrationNumber);
}
