package com.fuelconsumptionmanager.fuelconsumptionmanager.controller;

import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumption;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReport;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.FuelConsumptionReportRow;
import com.fuelconsumptionmanager.fuelconsumptionmanager.model.MonthlyExpense;
import com.fuelconsumptionmanager.fuelconsumptionmanager.repository.FuelConsumptionRepository;
import com.fuelconsumptionmanager.fuelconsumptionmanager.service.FuelConsumptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuelConsumptionControllerTest {

    @Autowired
    private FuelConsumptionService fuelConsumptionService;

    @MockBean
    private FuelConsumptionRepository fuelConsumptionRepository;

    private List<FuelConsumption> fuelConsumptions;

    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.fuelConsumptions = new ArrayList<>();

        this.fuelConsumptions.add(new FuelConsumption(2L, 1L, "95",
                new BigDecimal("1.367"), new BigDecimal("7"), new BigDecimal("9.569"),
                dateFormat.parse("2019-04-15")));
        this.fuelConsumptions.add(new FuelConsumption(3L, 2L, "95",
                new BigDecimal("1.417"), new BigDecimal("8"), new BigDecimal("11.336"),
                dateFormat.parse("2019-04-15")));
    }

    @Test
    public void getAllFuelConsumptionsTest() {
        when(fuelConsumptionRepository.findAll()).thenReturn(fuelConsumptions);
        assertEquals(2, fuelConsumptionService.getAllFuelConsumptions().size());
    }

    @Test
    public void getAllFuelConsumptionsByMonthNumberTest() {
        when(fuelConsumptionRepository.findAllByMonthNumber(4)).thenReturn(fuelConsumptions);
        assertEquals(2, fuelConsumptionService.getAllFuelConsumptionsByMonthNumber(4).size());
    }

    @Test
    public void getMonthlyExpensesTest() {
        when(fuelConsumptionRepository.findAll()).thenReturn(fuelConsumptions);

        MonthlyExpense monthlyExpense = new MonthlyExpense("April", new BigDecimal("20.905"));
        assertEquals(monthlyExpense, fuelConsumptionService.getMonthlyExpenses().get(0));
    }

    @Test
    public void getMonthlyFuelConsumptionTest() {
        when(fuelConsumptionRepository.findAll()).thenReturn(fuelConsumptions);

        FuelConsumptionReportRow aprilConsumptionsOF95 = new FuelConsumptionReportRow("95",
                new BigDecimal("15"), new BigDecimal("10.453"), new BigDecimal("20.905"));

        FuelConsumptionReport aprilConsumption = new FuelConsumptionReport("April",
                Collections.singletonList(aprilConsumptionsOF95));

        assertEquals(aprilConsumption, fuelConsumptionService.getMonthlyFuelConsumption().get(0));
    }

    @Test
    public void addFuelConsumptionTest() {
        FuelConsumption fuelConsumption = fuelConsumptions.get(0);
        fuelConsumption.setTotalPriceInEUR(null);

        when(fuelConsumptionRepository.save(fuelConsumption)).thenReturn(fuelConsumption);

        assertEquals(new BigDecimal("9.569"), fuelConsumptionService.addFuelConsumption(fuelConsumption)
                .getTotalPriceInEUR());
    }

    @Test
    public void addMultipleFuelConsumptionsTest() {
        FuelConsumption fuelConsumption1 = fuelConsumptions.get(0);
        fuelConsumption1.setTotalPriceInEUR(null);

        FuelConsumption fuelConsumption2 = fuelConsumptions.get(1);
        fuelConsumption2.setTotalPriceInEUR(null);

        when(fuelConsumptionRepository.saveAll(fuelConsumptions)).thenReturn(Arrays.asList(fuelConsumption1,
                fuelConsumption2));

        List<FuelConsumption> newFuelConsumptions = fuelConsumptionService
                .addMultipleFuelConsumptions(fuelConsumptions);

        assertEquals(new BigDecimal("9.569"), newFuelConsumptions.get(0).getTotalPriceInEUR());
        assertEquals(new BigDecimal("11.336"), newFuelConsumptions.get(1).getTotalPriceInEUR());
    }
}