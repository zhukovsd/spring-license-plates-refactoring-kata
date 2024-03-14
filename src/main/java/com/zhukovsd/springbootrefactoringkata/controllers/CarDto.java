package com.zhukovsd.springbootrefactoringkata.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {
    private String vin;
    private String model;
    private Integer yearOfManufacture;
    private String color;
    private String licensePlateNumber;
}
