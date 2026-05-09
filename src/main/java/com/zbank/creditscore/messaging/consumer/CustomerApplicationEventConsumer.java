package com.zbank.creditscore.messaging.consumer;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import com.zbank.creditscore.messaging.consumer.event.CustomerApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerApplicationEventConsumer {

    @KafkaListener(
            id = "zbank-customer-application-event-consumer",
            topics = "customerapplicationevent",
            groupId = "${zbank.kafka.consumer.customer-application.group-id}"
    )
    public void consumeCustomerApplicationEvent(CustomerApplicationEvent event) {
        log.info("Processing event for Application: {}", event.applicationId());
        ApplicantRequestDto dto = event.applicantData();

        // call the validation
    }
}