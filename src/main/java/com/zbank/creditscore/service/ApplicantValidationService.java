package com.zbank.creditscore.service;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ApplicantValidationService {

    public void validate(
            ApplicantRequestDto dto
    ) {

        validateAge(dto);

        validateSalary(dto);

        validateEmployment(dto);
    }

    private void validateAge(
            ApplicantRequestDto dto
    ) {

        int age = Period.between(
                dto.getDob(),
                LocalDate.now()
        ).getYears();

        if(age < 18) {

            throw new RuntimeException(
                    "Applicant age must be above 18"
            );
        }
    }

    private void validateSalary(
            ApplicantRequestDto dto
    ) {

        if(dto.getAnnualSalary()
                .compareTo(
                        BigDecimal.valueOf(25000)
                ) < 0) {

            throw new RuntimeException(
                    "Minimum salary criteria failed"
            );
        }
    }

    private void validateEmployment(
            ApplicantRequestDto dto
    ) {

        if(dto.getEmploymentType() == null
                || dto.getEmploymentType().isBlank()) {

            throw new RuntimeException(
                    "Employment type missing"
            );
        }
    }
}