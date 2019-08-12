package com.fuelconsumptionmanager.fuelconsumptionmanager.calculator;

import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConsumptionCalculator {

    public List<Map<String, Object>> getMoneyExpensesByMonths(List<FuelConsumption> fuelConsumptions) {
        Map<String, Double> records = getMonthlyExpenseRecords(fuelConsumptions);

        return records.entrySet().stream().map(expense -> {
            Map<String, Object> monthlyExpense = new HashMap<>();
            monthlyExpense.put("month", expense.getKey());
            monthlyExpense.put("totalSpentAmountOfMoney", expense.getValue());

            return monthlyExpense;
        }).collect(Collectors.toList());
    }

    private Map<String, Double> getMonthlyExpenseRecords(List<FuelConsumption> fuelConsumptions) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);

        return fuelConsumptions.stream().collect(Collectors.groupingBy(fc -> simpleDateFormat.format(fc.getDate()),
                Collectors.summingDouble(fc -> Math.round(fc.getTotalPriceInEUR() * 1000.0) / 1000.0)));
    }

    private List<Map<String, Object>> getMonthlyFuelConsumptionsRecords(List<FuelConsumption> fuelConsumptions) {
        Map<String, Double> volumeConsumptions = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(FuelConsumption::getFuelType,
                        Collectors.summingDouble(FuelConsumption::getVolumeInLiters)));

        Map<String, Double> averagePrice = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(FuelConsumption::getFuelType,
                        Collectors.averagingDouble(FuelConsumption::getPricePerLiterInEUR)));

        Map<String, Double> totalPrice = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(FuelConsumption::getFuelType,
                        Collectors.summingDouble(FuelConsumption::getTotalPriceInEUR)));

        List<Map<String, Object>> fuelConsumptionsByMonths = new ArrayList<>();
        for (Map.Entry<String, Double> volumeConsumption : volumeConsumptions.entrySet()) {
            Map<String, Object> consumption = new HashMap<>();
            double roundedAveragePrice = Math.round(averagePrice.get(volumeConsumption.getKey()) * 1000.0) / 1000.0;
            double roundedTotalPrice = Math.round(totalPrice.get(volumeConsumption.getKey()) * 1000.0) / 1000.0;

            consumption.put("fuelType", volumeConsumption.getKey());
            consumption.put("volume", volumeConsumptions.get(volumeConsumption.getKey()));
            consumption.put("averagePrice", roundedAveragePrice);
            consumption.put("totalPrice", roundedTotalPrice);

            fuelConsumptionsByMonths.add(consumption);
        }

        return fuelConsumptionsByMonths;
    }

    public List<Map<String, Object>> getFuelConsumptionStatistic(List<FuelConsumption> fuelConsumptions) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);

        Map<String, List<FuelConsumption>> monthlyConsumptions = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(fc -> simpleDateFormat.format(fc.getDate())));

        List<Map<String, Object>> fuelConsumptionByMonths = new ArrayList<>();
        for (Map.Entry<String, List<FuelConsumption>> monthlyConsumption : monthlyConsumptions.entrySet()) {
            Map<String, Object> consumption = new HashMap<>();
            consumption.put("month", monthlyConsumption.getKey());
            consumption.put("consumptions", getMonthlyFuelConsumptionsRecords(monthlyConsumption.getValue()));
            fuelConsumptionByMonths.add(consumption);
        }

        return fuelConsumptionByMonths;
    }
}
