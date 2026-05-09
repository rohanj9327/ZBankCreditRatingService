package com.zbank.creditscore.service;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditScoreService {

    public CreditDecision calculate(
            ApplicantRequestDto dto
    ) {

        Integer existingCards =
                dto.getExistingCreditCards();

        BigDecimal salary =
                dto.getAnnualSalary();

        /*
            HIGH PROFILE
         */
        if(existingCards >= 3 &&
                salary.compareTo(
                        BigDecimal.valueOf(700000)
                ) > 0) {

            return new CreditDecision(
                    800,
                    "PASSED",
                    "PLATINUM",
                    null
            );
        }

        /*
            MID PROFILE
         */
        if(existingCards >= 1 &&
                salary.compareTo(
                        BigDecimal.valueOf(300000)
                ) > 0) {

            return new CreditDecision(
                    650,
                    "PASSED",
                    "GOLD",
                    null
            );
        }

        /*
            BASIC PROFILE
         */
        if(salary.compareTo(
                BigDecimal.valueOf(100000)
        ) > 0) {

            return new CreditDecision(
                    500,
                    "PASSED",
                    "VISA",
                    null
            );
        }

        /*
            FAILED
         */
        return new CreditDecision(
                300,
                "FAILED",
                null,
                "Credit policy validation failed"
        );
    }
}