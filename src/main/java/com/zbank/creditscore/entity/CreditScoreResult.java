package com.zbank.creditscore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_score_result")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditScoreResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long applicationId;

    private Integer score;

    private String decision;

    private String cardType;

    private Long creditLimit;

    private LocalDateTime processedAt;
}