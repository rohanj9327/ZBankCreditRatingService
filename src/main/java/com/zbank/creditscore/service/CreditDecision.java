package com.zbank.creditscore.service;

public record CreditDecision(

        Integer score,

        String status,

        String cardType,

        String failureReason
) {
}