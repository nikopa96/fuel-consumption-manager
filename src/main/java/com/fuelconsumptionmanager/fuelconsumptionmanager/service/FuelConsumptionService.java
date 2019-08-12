package com.fuelconsumptionmanager.fuelconsumptionmanager.service;

import com.fuelconsumptionmanager.fuelconsumptionmanager.calculator.ConsumptionCalculator;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import com.fuelconsumptionmanager.fuelconsumptionmanager.repository.FuelConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuelConsumptionService {

    private final FuelConsumptionRepository fuelConsumptionRepository;

    @Autowired
    public FuelConsumptionService(FuelConsumptionRepository fuelConsumptionRepository) {
        this.fuelConsumptionRepository = fuelConsumptionRepository;
    }

    public List<FuelConsumption> getAllFuelConsumptions() {
        return fuelConsumptionRepository.findAll();
    }

    public List<FuelConsumption> getAllFuelConsumptionsByMonthNumber(Integer monthNumber) {
        return fuelConsumptionRepository.findAllByMonthNumber(monthNumber);
    }

    public List<FuelConsumption> getAllFuelConsumptionsByMonthNumberAndDriverId(Integer monthNumber, Long driverId) {
        return fuelConsumptionRepository.findAllByMonthNumberAndDriverId(monthNumber, driverId);
    }

    public List<Map<String, Object>> getMonthlyExpenses() {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAll();
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getMoneyExpensesByMonths(fuelConsumptions);
    }

    public Map<String, Object> getMonthlyExpensesByDriverId(Long driverId) {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAllByDriverId(driverId);
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        Map<String, Object> monthlyExpenses = new HashMap<>();
        monthlyExpenses.put("driverId", driverId);
        monthlyExpenses.put("expenses", consumptionCalculator.getMoneyExpensesByMonths(fuelConsumptions));

        return monthlyExpenses;
    }

    public List<Map<String, Object>> getMonthlyFuelConsumption() {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAll();
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getFuelConsumptionStatistic(fuelConsumptions);
    }

    public List<Map<String, Object>> getMonthlyFuelConsumptionByDriverId(Long driverId) {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAllByDriverId(driverId);
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getFuelConsumptionStatistic(fuelConsumptions);
    }

    public FuelConsumption addFuelConsumption(FuelConsumption fuelConsumption) {
        if (fuelConsumption.getTotalPriceInEUR() == null) {
            fuelConsumption.setTotalPriceInEUR(fuelConsumption.getVolumeInLiters() * fuelConsumption.getPricePerLiterInEUR());
        }

        fuelConsumptionRepository.save(fuelConsumption);
        return fuelConsumption;
    }

    public List<FuelConsumption> addMultipleFuelConsumptions(List<FuelConsumption> fuelConsumptions) {
        fuelConsumptions.forEach(fuelConsumption -> {
            if (fuelConsumption.getTotalPriceInEUR() == null) {
                fuelConsumption.setTotalPriceInEUR(fuelConsumption.getVolumeInLiters() * fuelConsumption.getPricePerLiterInEUR());
            }
        });

        fuelConsumptionRepository.saveAll(fuelConsumptions);
        return fuelConsumptions;
    }
}
