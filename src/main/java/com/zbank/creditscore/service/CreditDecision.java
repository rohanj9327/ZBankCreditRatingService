package com.zbank.creditscore.service;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_decisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private String status;

    private String cardType;

    private String failureReason;

    // Manual constructor to match your Service's "new CreditDecision(...)" calls
    public CreditDecision(Integer score, String status, String cardType, String failureReason) {
        this.score = score;
        this.status = status;
        this.cardType = cardType;
        this.failureReason = failureReason;
    }
}