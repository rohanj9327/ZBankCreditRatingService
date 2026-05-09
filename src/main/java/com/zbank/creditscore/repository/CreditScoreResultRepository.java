package com.zbank.creditscore.repository;

import com.zbank.creditscore.entity.CreditScoreResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditScoreResultRepository
        extends JpaRepository<
        CreditScoreResult,
        UUID
        > {
}