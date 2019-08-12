package com.fuelconsumptionmanager.fuelconsumptionmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelConsumptionReportRow {

    private String fuelType;
    private BigDecimal volume;
    private BigDecimal averagePrice;
    private BigDecimal totalPrice;
}
