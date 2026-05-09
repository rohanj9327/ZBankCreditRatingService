package com.zbank.creditscore.service;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import com.zbank.creditscore.repository.CreditDecisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreditScoreService {

    private final CreditDecisionRepository repository;

    public CreditDecision calculate(ApplicantRequestDto dto) {
        Integer existingCards = dto.getExistingCreditCards();
        BigDecimal salary = dto.getAnnualSalary();

        if(existingCards >= 3 && salary.compareTo(BigDecimal.valueOf(700000)) > 0) {
            CreditDecision decision = new CreditDecision(800, "PASSED", "PLATINUM", null);

            repository.save(decision);
            return decision;
        }

        if(existingCards >= 1 && salary.compareTo(BigDecimal.valueOf(300000)) > 0) {
            CreditDecision decision = new CreditDecision(650, "PASSED", "GOLD", null);

            repository.save(decision);
            return decision;
        }

        if(salary.compareTo(BigDecimal.valueOf(100000)) > 0) {
            CreditDecision decision = new CreditDecision(500, "PASSED", "VISA", null);

            repository.save(decision);
            return decision;
        }

        CreditDecision decision =  new CreditDecision(300, "FAILED", null, "Credit policy validation failed");
        repository.save(decision);
        return decision;
    }
}
