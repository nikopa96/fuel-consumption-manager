package com.fuelconsumptionmanager.fuelconsumptionmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "fuel_consumption")
@Getter
@Setter
public class FuelConsumption implements Serializable {

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
    private Float pricePerLiterInEUR;

    @NotNull
    @Positive
    @Column(name = "volume_in_liters")
    private Float volumeInLiters;

    @Positive
    @Column(name = "total_price_in_eur")
    private Float totalPriceInEUR;

    @NotNull
    @Column(name = "date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;
}
