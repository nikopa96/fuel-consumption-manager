package com.fuelconsumptionmanager.fuelconsumptionmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelConsumptionReport {

    private String month;
    private List<FuelConsumptionReportRow> consumptions;
}
