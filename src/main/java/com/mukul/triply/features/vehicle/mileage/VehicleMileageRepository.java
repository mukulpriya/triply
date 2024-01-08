package com.mukul.triply.features.vehicle.mileage;

import com.mukul.triply.common.baseclasses.BaseRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

@Repository
public interface VehicleMileageRepository extends BaseRepository<VehicleMileageEntity> {

    Optional<VehicleMileageEntity> findByVehicleIdAndYearAndMonthAndWeek(final String vehicleId, final Year year, final Month month, final Integer week);

    Optional<VehicleMileageEntity> findByVehicleIdAndYearAndMonthAndWeekAndIsValid(String vehicleId, Year year, Month month, Integer week, Boolean isValid);
}
