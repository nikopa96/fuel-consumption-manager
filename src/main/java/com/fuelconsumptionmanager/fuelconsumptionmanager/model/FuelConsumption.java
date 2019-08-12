package com.fuelconsumptionmanager.fuelconsumptionmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "fuel_consumption")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelConsumption {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "fuel_consumption_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @Column(name = "fuel_consumption_id")
    private Long fuelConsumptionId;

    @NotNull
    @Column(name = "driver_id")
    private Long driverId;

    @NotNull
    @Column(name = "fuel_type")
    private String fuelType;

    @NotNull
    @Positive
    @Column(name = "price_per_liter_in_eur")
    private BigDecimal pricePerLiterInEUR;

    @NotNull
    @Positive
    @Column(name = "volume_in_liters")
    private BigDecimal volumeInLiters;

    @Positive
    @Column(name = "total_price_in_eur")
    private BigDecimal totalPriceInEUR;

    @NotNull
    @Column(name = "date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;
}
