package com.zhukovsd.springbootrefactoringkata.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhukovsd.springbootrefactoringkata.models.Car;
import com.zhukovsd.springbootrefactoringkata.models.LicensePlate;
import com.zhukovsd.springbootrefactoringkata.repositories.CarRepository;
import com.zhukovsd.springbootrefactoringkata.repositories.LicensePlateRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    private final CarRepository carRepository;
    private final LicensePlateRepository licensePlateRepository;

    public CarController(CarRepository carRepository, LicensePlateRepository licensePlateRepository) {
        this.carRepository = carRepository;
        this.licensePlateRepository = licensePlateRepository;
    }

    @GetMapping("/cars")
    public List<CarDto> getCars(
            @RequestParam(defaultValue = "false") boolean onlyWithoutLicensePlate
    ) {
        List<Car> cars;
        if (onlyWithoutLicensePlate) {
            cars = carRepository.findByLicensePlateNull();
        } else {
            cars = carRepository.findAll();
        }

        return cars.stream()
                .map(car -> new CarDto(
                        car.getVin(),
                        car.getModel(),
                        car.getYearOfManufacture(),
                        car.getColor(),
                        car.getLicensePlate() == null ? null : car.getLicensePlate().getNumber()
                ))
                .toList();
    }

    @PostMapping("/car")
    // read request parameters from form data
    public ResponseEntity<JsonNode> createCar(
            @RequestParam String vin,
            @RequestParam String model,
            @RequestParam int yearOfManufacture,
            @RequestParam String color
    ) {
        // check if car with given VIN already exists
        if (carRepository.existsByVin(vin)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Car car = new Car(vin, model, yearOfManufacture, color);
        carRepository.save(car);

        CarDto dto = new CarDto(
                car.getVin(),
                car.getModel(),
                car.getYearOfManufacture(),
                car.getColor(),
                null
        );

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.valueToTree(dto);

        // return response body as JSON with code 201
        return new ResponseEntity<>(jsonNode, HttpStatus.CREATED);
    }

    // assign license plate to car
    @PostMapping("/car/{vin}/license-plate")
    public ResponseEntity<JsonNode> assignLicensePlate(
            @PathVariable String vin,
            @RequestParam String number
    ) {
        Car car = carRepository.findByVin(vin);

        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!number.isEmpty()) {
            LicensePlate licensePlate = licensePlateRepository.findByNumber(number);

            if (licensePlate == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // check if license plate is already assigned to a car
            if (carRepository.findByLicensePlate_Id(licensePlate.getId()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            car.setLicensePlate(licensePlate);
            carRepository.save(car);
        } else {
            car.setLicensePlate(null);
            carRepository.save(car);
        }

        CarDto dto = new CarDto(
                car.getVin(),
                car.getModel(),
                car.getYearOfManufacture(),
                car.getColor(),
                car.getLicensePlate() != null ? car.getLicensePlate().getNumber() : null
        );

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.valueToTree(dto);

        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }
}
