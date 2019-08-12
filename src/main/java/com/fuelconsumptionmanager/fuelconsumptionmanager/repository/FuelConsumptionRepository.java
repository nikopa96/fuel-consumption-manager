package com.fuelconsumptionmanager.fuelconsumptionmanager.repository;

import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelConsumptionRepository extends JpaRepository<FuelConsumption, Long> {

    List<FuelConsumption> findAllByDriverId(Long driverId);

    @Query("select fc from FuelConsumption fc where month(fc.date) = :monthNumber")
    List<FuelConsumption> findAllByMonthNumber(@Param("monthNumber") Integer monthNumber);

    @Query("select fc from FuelConsumption fc where fc.driverId = :driverId and month(fc.date) = :monthNumber")
    List<FuelConsumption> findAllByMonthNumberAndDriverId(@Param("monthNumber") Integer monthNumber,
                                                          @Param("driverId") Long driverId);
}
