package com.zhukovsd.springbootrefactoringkata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "license_plates")
@Getter
@Setter
public class LicensePlate {
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "license_plates_id_seq")
    private Long id;

    private String number;
}
