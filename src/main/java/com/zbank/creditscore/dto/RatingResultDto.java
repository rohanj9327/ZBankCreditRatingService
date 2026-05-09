package com.zbank.creditscore.dto;

import lombok.Builder;

@Builder
public record RatingResultDto(

        String status,

        Integer score,

        String cardType,

        Long applicationId,

        String failureReason
) {
}