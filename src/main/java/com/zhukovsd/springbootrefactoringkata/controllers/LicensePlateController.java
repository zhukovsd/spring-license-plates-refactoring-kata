package com.zhukovsd.springbootrefactoringkata.controllers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhukovsd.springbootrefactoringkata.models.LicensePlate;
import com.zhukovsd.springbootrefactoringkata.repositories.LicensePlateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * License plate controller. Operations:
 * - get all license plates
 * - get license plates count
 * - issue N license plates starting from the last existing one
 */
@RestController
public class LicensePlateController {
    private final LicensePlateRepository licensePlateRepository;

    public LicensePlateController(LicensePlateRepository licensePlateRepository) {
        this.licensePlateRepository = licensePlateRepository;
    }

    @GetMapping("/license-plates")
    public List<LicensePlate> getAllLicensePlates() {
        return licensePlateRepository.findAll();
    }

    @GetMapping("/license-plates/count")
    public JsonNode getLicensePlatesCount() {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("count", licensePlateRepository.findAll().size());

        return jsonObject;
    }

    // create N license plates starting from the last existing one
    @PostMapping("/license-plates/issue")
    public List<LicensePlate> issueLicensePlates(
            @RequestParam(value = "count", defaultValue = "2") int count
    ) {
        List<LicensePlate> licensePlates = licensePlateRepository.findAll();
        long lastNumber = Long.parseLong(licensePlates.get(licensePlates.size() - 1).getNumber());

        List<LicensePlate> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            LicensePlate licensePlate = new LicensePlate();

            licensePlate.setNumber(String.format("%04d", lastNumber + i + 1));
            licensePlateRepository.save(licensePlate);

            result.add(licensePlate);
        }

        return result;
    }
}
