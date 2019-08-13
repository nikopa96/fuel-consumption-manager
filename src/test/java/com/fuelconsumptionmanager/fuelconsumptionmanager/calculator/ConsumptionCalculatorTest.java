package com.fuelconsumptionmanager.fuelconsumptionmanager.calculator;

import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReport;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReportRow;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.MonthlyExpense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ConsumptionCalculatorTest {

    private List<FuelConsumption> fuelConsumptions;
    private ConsumptionCalculator calculator;

    @BeforeEach
    void setUp() throws ParseException {
        this.fuelConsumptions = new ArrayList<>();
        this.calculator = new ConsumptionCalculator();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.fuelConsumptions.add(new FuelConsumption(1L, 1L, "D",
                new BigDecimal("1.337"), new BigDecimal("5"), new BigDecimal("6.685"),
                dateFormat.parse("2019-04-11")));
        this.fuelConsumptions.add(new FuelConsumption(2L, 1L, "95",
                new BigDecimal("1.367"), new BigDecimal("7"), new BigDecimal("9.569"),
                dateFormat.parse("2019-04-15")));
        this.fuelConsumptions.add(new FuelConsumption(3L, 2L, "95",
                new BigDecimal("1.417"), new BigDecimal("8"), new BigDecimal("11.336"),
                dateFormat.parse("2019-04-15")));
        this.fuelConsumptions.add(new FuelConsumption(4L, 2L, "98",
                new BigDecimal("1.417"), new BigDecimal("4"), new BigDecimal("5.668"),
                dateFormat.parse("2019-05-04")));
        this.fuelConsumptions.add(new FuelConsumption(5L, 3L, "98",
                new BigDecimal("1.325"), new BigDecimal("8"), new BigDecimal("10.6"),
                dateFormat.parse("2019-05-05")));
        this.fuelConsumptions.add(new FuelConsumption(6L, 3L, "D",
                new BigDecimal("1.415"), new BigDecimal("7"), new BigDecimal("9.905"),
                dateFormat.parse("2019-06-05")));
        this.fuelConsumptions.add(new FuelConsumption(7L, 1L, "95",
                new BigDecimal("1.515"), new BigDecimal("10"), new BigDecimal("15.15"),
                dateFormat.parse("2019-06-05")));
    }

    @Test
    void getMoneyExpensesByMonthsTest() {
        List<MonthlyExpense> expenses = calculator.getMoneyExpensesByMonths(fuelConsumptions);
        Map<String, List<MonthlyExpense>> expensesByMonths = expenses.stream()
                .collect(Collectors.groupingBy(MonthlyExpense::getMonth));

        BigDecimal aprilMoneyExpense = expensesByMonths.get("April").get(0).getTotalSpentAmountOfMoney();
        BigDecimal mayMoneyExpense = expensesByMonths.get("May").get(0).getTotalSpentAmountOfMoney();
        BigDecimal juneMoneyExpense = expensesByMonths.get("June").get(0).getTotalSpentAmountOfMoney();

        assertEquals(new BigDecimal("27.590"), aprilMoneyExpense);
        assertEquals(new BigDecimal("16.268"), mayMoneyExpense);
        assertEquals(new BigDecimal("25.055"), juneMoneyExpense);
    }

    @Test
    void getMonthlyFuelConsumptionReportsTest() {
        List<FuelConsumptionReport> monthlyReports = calculator.getMonthlyFuelConsumptionReports(fuelConsumptions);

        Map<String, List<FuelConsumptionReport>> reports = monthlyReports.stream()
                .collect(Collectors.groupingBy(FuelConsumptionReport::getMonth));

        Map<String, List<FuelConsumptionReportRow>> aprilConsumptionReports = reports.get("April").get(0)
                .getConsumptions().stream().collect(Collectors.groupingBy(FuelConsumptionReportRow::getFuelType));
        Map<String, List<FuelConsumptionReportRow>> mayConsumptionReports = reports.get("May").get(0)
                .getConsumptions().stream().collect(Collectors.groupingBy(FuelConsumptionReportRow::getFuelType));
        Map<String, List<FuelConsumptionReportRow>> juneConsumptionReports = reports.get("June").get(0)
                .getConsumptions().stream().collect(Collectors.groupingBy(FuelConsumptionReportRow::getFuelType));

        FuelConsumptionReportRow aprilConsumptionOfD = aprilConsumptionReports.get("D").get(0);
        FuelConsumptionReportRow aprilConsumptionOf95 = aprilConsumptionReports.get("95").get(0);
        FuelConsumptionReportRow mayConsumptionOf98 = mayConsumptionReports.get("98").get(0);
        FuelConsumptionReportRow juneConsumptionOf98 = juneConsumptionReports.get("D").get(0);
        FuelConsumptionReportRow juneConsumptionOf95 = juneConsumptionReports.get("95").get(0);

        assertEquals(new FuelConsumptionReportRow("D", new BigDecimal("5"), new BigDecimal("6.685"),
                new BigDecimal("6.685")), aprilConsumptionOfD);
        assertEquals(new FuelConsumptionReportRow("95", new BigDecimal("15"), new BigDecimal("10.453"),
                new BigDecimal("20.905")), aprilConsumptionOf95);
        assertEquals(new FuelConsumptionReportRow("98", new BigDecimal("12"), new BigDecimal("8.134"),
                new BigDecimal("16.268")), mayConsumptionOf98);
        assertEquals(new FuelConsumptionReportRow("D", new BigDecimal("7"), new BigDecimal("9.905"),
                new BigDecimal("9.905")), juneConsumptionOf98);
        assertEquals(new FuelConsumptionReportRow("95", new BigDecimal("10"), new BigDecimal("15.15"),
                new BigDecimal("15.15")), juneConsumptionOf95);
    }
}