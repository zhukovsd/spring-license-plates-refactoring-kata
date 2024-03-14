package com.zhukovsd.springbootrefactoringkata.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(generator = "cars_id_seq")
    private Long id;

    private String vin;
    private String model;
    private Integer yearOfManufacture;
    private String color;

    @OneToOne
    private LicensePlate licensePlate;

    public Car(String vin, String model, int yearOfManufacture, String color) {
        this.vin = vin;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.color = color;
    }
}
