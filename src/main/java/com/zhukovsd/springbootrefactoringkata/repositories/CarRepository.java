package com.zhukovsd.springbootrefactoringkata.repositories;

import com.zhukovsd.springbootrefactoringkata.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findByLicensePlateNull();

    public boolean existsByVin(String vin);

    public Car findByVin(String vin);

    public Car findByLicensePlate_Id(Long id);
}
