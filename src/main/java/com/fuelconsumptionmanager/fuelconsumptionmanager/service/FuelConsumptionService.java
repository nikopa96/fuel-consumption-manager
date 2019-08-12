package com.fuelconsumptionmanager.fuelconsumptionmanager.service;

import com.fuelconsumptionmanager.fuelconsumptionmanager.calculator.ConsumptionCalculator;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReport;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.MonthlyExpense;
import com.fuelconsumptionmanager.fuelconsumptionmanager.repository.FuelConsumptionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelConsumptionService {

    @NonNull
    private FuelConsumptionRepository fuelConsumptionRepository;

    public List<FuelConsumption> getAllFuelConsumptions() {
        return fuelConsumptionRepository.findAll();
    }

    public List<FuelConsumption> getAllFuelConsumptionsByMonthNumber(Integer monthNumber) {
        return fuelConsumptionRepository.findAllByMonthNumber(monthNumber);
    }

    public List<FuelConsumption> getAllFuelConsumptionsByMonthNumberAndDriverId(Integer monthNumber, Long driverId) {
        return fuelConsumptionRepository.findAllByMonthNumberAndDriverId(monthNumber, driverId);
    }

    public List<MonthlyExpense> getMonthlyExpenses() {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAll();
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getMoneyExpensesByMonths(fuelConsumptions);
    }

    public List<MonthlyExpense> getMonthlyExpensesByDriverId(Long driverId) {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAllByDriverId(driverId);
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getMoneyExpensesByMonths(fuelConsumptions);
    }

    public List<FuelConsumptionReport> getMonthlyFuelConsumption() {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAll();
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getMonthlyFuelConsumptionReports(fuelConsumptions);
    }

    public List<FuelConsumptionReport> getMonthlyFuelConsumptionByDriverId(Long driverId) {
        List<FuelConsumption> fuelConsumptions = fuelConsumptionRepository.findAllByDriverId(driverId);
        ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();

        return consumptionCalculator.getMonthlyFuelConsumptionReports(fuelConsumptions);
    }

    public FuelConsumption addFuelConsumption(FuelConsumption fuelConsumption) {
        if (fuelConsumption.getTotalPriceInEUR() == null) {
            fuelConsumption.setTotalPriceInEUR(fuelConsumption.getVolumeInLiters()
                    .multiply(fuelConsumption.getPricePerLiterInEUR()));
        }

        fuelConsumptionRepository.save(fuelConsumption);
        return fuelConsumption;
    }

    public List<FuelConsumption> addMultipleFuelConsumptions(List<FuelConsumption> fuelConsumptions) {
        fuelConsumptions.forEach(fuelConsumption -> {
            if (fuelConsumption.getTotalPriceInEUR() == null) {
                fuelConsumption.setTotalPriceInEUR(fuelConsumption.getVolumeInLiters()
                        .multiply(fuelConsumption.getPricePerLiterInEUR()));
            }
        });

        fuelConsumptionRepository.saveAll(fuelConsumptions);
        return fuelConsumptions;
    }
}
