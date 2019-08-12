package com.fuelconsumptionmanager.fuelconsumptionmanager.calculator;

import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReport;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReportRow;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.MonthlyExpense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConsumptionCalculator {

    private Map<String, BigDecimal> getMonthlyExpenseRecords(List<FuelConsumption> fuelConsumptions) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);

        return fuelConsumptions.stream().collect(Collectors.groupingBy(fc -> simpleDateFormat.format(fc.getDate()),
                Collectors.reducing(BigDecimal.ZERO, FuelConsumption::getTotalPriceInEUR, BigDecimal::add)));
    }

    public List<MonthlyExpense> getMoneyExpensesByMonths(List<FuelConsumption> fuelConsumptions) {
        Map<String, BigDecimal> records = getMonthlyExpenseRecords(fuelConsumptions);

        return records.entrySet().stream().map(expense -> new MonthlyExpense(expense.getKey(), expense.getValue()))
                .collect(Collectors.toList());
    }

    private List<FuelConsumptionReportRow> getMonthlyFuelConsumptionReportRows(List<FuelConsumption> fuelConsumptions) {
        Map<String, BigDecimal> volumeConsumptions = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(FuelConsumption::getFuelType,
                        Collectors.reducing(BigDecimal.ZERO, FuelConsumption::getVolumeInLiters, BigDecimal::add)));

        Map<String, BigDecimal> totalPrice = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(FuelConsumption::getFuelType,
                        Collectors.reducing(BigDecimal.ZERO, FuelConsumption::getTotalPriceInEUR, BigDecimal::add)));

        Map<String, Long> numberOfConsumptions = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(FuelConsumption::getFuelType, Collectors.counting()));

        List<FuelConsumptionReportRow> reportRows = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> volumeConsumption : volumeConsumptions.entrySet()) {
            FuelConsumptionReportRow reportRow = new FuelConsumptionReportRow();

            BigDecimal averagePrice = totalPrice.get(volumeConsumption.getKey())
                    .divide(new BigDecimal(numberOfConsumptions.get(volumeConsumption.getKey())), RoundingMode.HALF_UP);

            reportRow.setFuelType(volumeConsumption.getKey());
            reportRow.setVolume(volumeConsumptions.get(volumeConsumption.getKey()));
            reportRow.setTotalPrice(totalPrice.get(volumeConsumption.getKey()));
            reportRow.setAveragePrice(averagePrice);

            reportRows.add(reportRow);
        }

        return reportRows;
    }

    public List<FuelConsumptionReport> getMonthlyFuelConsumptionReports(List<FuelConsumption> fuelConsumptions) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);

        Map<String, List<FuelConsumption>> monthlyConsumptions = fuelConsumptions.stream()
                .collect(Collectors.groupingBy(fc -> dateFormat.format(fc.getDate())));

        List<FuelConsumptionReport> fuelConsumptionReports = new ArrayList<>();
        for (Map.Entry<String, List<FuelConsumption>> monthlyConsumption : monthlyConsumptions.entrySet()) {
            FuelConsumptionReport report = new FuelConsumptionReport();
            report.setMonth(monthlyConsumption.getKey());
            report.setConsumptions(getMonthlyFuelConsumptionReportRows(monthlyConsumption.getValue()));

            fuelConsumptionReports.add(report);
        }

        return fuelConsumptionReports;
    }
}
