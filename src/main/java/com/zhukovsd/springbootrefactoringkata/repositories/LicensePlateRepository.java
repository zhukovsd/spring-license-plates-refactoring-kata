package com.zhukovsd.springbootrefactoringkata.repositories;

import com.zhukovsd.springbootrefactoringkata.models.LicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> {
    public LicensePlate findByNumber(String number);
}
