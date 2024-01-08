package com.mukul.triply.features.vehicle.mileage;

import com.mukul.triply.common.baseclasses.BaseService;
import com.mukul.triply.exception.NotFoundException;
import com.mukul.triply.features.vehicle.VehicleEntity;
import com.mukul.triply.features.vehicle.VehicleEntry;
import com.mukul.triply.features.vehicle.VehicleMapper;
import com.mukul.triply.features.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class VehicleMileageService extends BaseService<VehicleMileageEntity, VehicleMileageEntry> {

    private final VehicleMileageRepository vehicleMileageRepository;

    private final VehicleRepository vehicleRepository;
    @Autowired
    public VehicleMileageService(final VehicleMileageRepository repository, VehicleMileageRepository vehicleMileageRepository, VehicleRepository vehicleRepository) {
        super();
        this.vehicleMileageRepository = vehicleMileageRepository;
        this.vehicleRepository = vehicleRepository;
        this.repository = repository;
        this.mapper = VehicleMileageMapper.INSTANCE;
    }

    public Optional<VehicleMileageEntity> getBy(final String vehicleId, final Year year, final Month month, final Integer week, final Boolean isValid) {
        return vehicleMileageRepository.findByVehicleIdAndYearAndMonthAndWeekAndIsValid(vehicleId, year, month, week, isValid);

    }

    public void upsertVehicleMileage(final VehicleEntity vehicleEntity, VehicleMileageEntry vehicleMileage) {

        final Optional<VehicleMileageEntity> optionalVehicleMileage = getBy(vehicleEntity.getId(), vehicleMileage.getYear(), vehicleMileage.getMonth(), vehicleMileage.getWeek(), Boolean.TRUE);

        ArrayList<VehicleMileageEntity> vehicleMileageEntityList = new ArrayList<>();
        VehicleMileageEntity entity = VehicleMileageMapper.INSTANCE.toEntity(vehicleMileage);
        entity.setVehicle(vehicleEntity);

        vehicleMileageEntityList.add(entity);

        if (!optionalVehicleMileage.isEmpty()) {
            final VehicleMileageEntity vehicleMileageEntity = optionalVehicleMileage.get();
            vehicleMileageEntity.setIsValid(Boolean.FALSE);
            vehicleMileageEntityList.add(vehicleMileageEntity);
        }

        this.repository.saveAll(vehicleMileageEntityList);
    }
}
