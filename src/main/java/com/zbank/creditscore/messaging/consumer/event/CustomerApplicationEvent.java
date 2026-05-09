package com.zbank.creditscore.messaging.consumer.event;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import lombok.Builder;

@Builder
public record CustomerApplicationEvent(

        Long applicationId,

        String eventType,

        ApplicantRequestDto applicantData
) {
}