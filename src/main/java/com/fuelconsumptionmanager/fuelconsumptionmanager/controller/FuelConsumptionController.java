package com.fuelconsumptionmanager.fuelconsumptionmanager.controller;

import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReport;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.MonthlyExpense;
import com.fuelconsumptionmanager.fuelconsumptionmanager.service.FuelConsumptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "fuelConsumption")
@RequiredArgsConstructor
public class FuelConsumptionController {

    @NonNull
    private FuelConsumptionService fuelConsumptionService;

    @GetMapping(path = "/getAll")
    @CrossOrigin
    public List<FuelConsumption> getAllFuelConsumptions() {
        return fuelConsumptionService.getAllFuelConsumptions();
    }

    @GetMapping(path = "/getAllByMonth/{monthNumber}")
    @CrossOrigin
    public List<FuelConsumption> getAllFuelConsumptionsByMonthNumber(@PathVariable("monthNumber") Integer monthNumber) {
        return fuelConsumptionService.getAllFuelConsumptionsByMonthNumber(monthNumber);
    }

    @GetMapping(path = "/getAllByMonth/{monthNumber}/{driverId}")
    @CrossOrigin
    public List<FuelConsumption> getAllFuelConsumptionsByMonthNumberAndDriverId(
            @PathVariable("monthNumber") Integer monthNumber, @PathVariable("driverId") Long driverId) {
        return fuelConsumptionService.getAllFuelConsumptionsByMonthNumberAndDriverId(monthNumber, driverId);
    }

    @GetMapping(path = "/getMonthlyExpenses")
    @CrossOrigin
    public List<MonthlyExpense> getMonthlyExpenses() {
        return fuelConsumptionService.getMonthlyExpenses();
    }

    @GetMapping(path = "/getMonthlyExpenses/{driverId}")
    @CrossOrigin
    public List<MonthlyExpense> getMonthlyExpensesByDriverId(@PathVariable("driverId") Long driverId) {
        return fuelConsumptionService.getMonthlyExpensesByDriverId(driverId);
    }

    @GetMapping(path = "/getMonthlyFuelConsumption")
    @CrossOrigin
    public List<FuelConsumptionReport> getMonthlyFuelConsumption() {
        return fuelConsumptionService.getMonthlyFuelConsumption();
    }

    @GetMapping(path = "/getMonthlyFuelConsumption/{driverId}")
    @CrossOrigin
    public List<FuelConsumptionReport> getMonthlyFuelConsumptionByDriverId(@PathVariable("driverId") Long driverId) {
        return fuelConsumptionService.getMonthlyFuelConsumptionByDriverId(driverId);
    }

    @PostMapping(path = "/add")
    @CrossOrigin
    public FuelConsumption addFuelConsumption(@RequestBody @Valid FuelConsumption fuelConsumption) {
        return fuelConsumptionService.addFuelConsumption(fuelConsumption);
    }

    @PostMapping(path = "/addAll")
    @CrossOrigin
    public List<FuelConsumption> addMultipleFuelConsumptions(@RequestBody @Valid List<FuelConsumption> fuelConsumptions) {
        return fuelConsumptionService.addMultipleFuelConsumptions(fuelConsumptions);
    }
}
