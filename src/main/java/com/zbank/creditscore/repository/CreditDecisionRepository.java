package com.zbank.creditscore.repository;

import com.zbank.creditscore.service.CreditDecision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditDecisionRepository extends JpaRepository<CreditDecision, Long> {}