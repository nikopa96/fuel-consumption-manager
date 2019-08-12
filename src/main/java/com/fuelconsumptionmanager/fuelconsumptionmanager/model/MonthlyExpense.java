package com.fuelconsumptionmanager.fuelconsumptionmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyExpense {

    private String month;
    private BigDecimal totalSpentAmountOfMoney;
}
