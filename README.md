Value: { "status": "Passed", "score": 500, "cardType": "PLATINUM", “applicationid”: “app3242” , “failureReason”: “rft”   }


package com.zbank.creditscore.dto;

import lombok.Builder;

@Builder
public record RatingResultDto(
    String status,
    Integer score,
    String cardType,
    String applicationId,
    String failureReason
) {}
