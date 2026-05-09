package com.zbank.creditscore.messaging.consumer;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import com.zbank.creditscore.dto.RatingResultDto;
import com.zbank.creditscore.messaging.consumer.event.CustomerApplicationEvent;
import com.zbank.creditscore.messaging.producer.RatingResultProducer;
import com.zbank.creditscore.service.ApplicantValidationService;
import com.zbank.creditscore.service.CreditDecision;
import com.zbank.creditscore.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerApplicationEventConsumer {

    private final ApplicantValidationService
            validationService;

    private final CreditScoreService
            scoreService;

    private final RatingResultProducer
            producer;

    @KafkaListener(
            id = "zbank-customer-application-event-consumer",
            topics = "customerapplicationevent",
            groupId = "${zbank.kafka.consumer.customer-application.group-id}"
    )
    public void consumeCustomerApplicationEvent(
            CustomerApplicationEvent event
    ) {

        log.info(
                "Processing application: {}",
                event.applicationId()
        );

        try {

            ApplicantRequestDto dto =
                    event.applicantData();

            /*
                Validation
             */
            validationService.validate(dto);

            /*
                Score Calculation
             */
            CreditDecision decision =
                    scoreService.calculate(dto);

            /*
                Build Result
             */
            RatingResultDto result =
                    RatingResultDto.builder()

                            .status(
                                    decision.status()
                            )

                            .score(
                                    decision.score()
                            )

                            .cardType(
                                    decision.cardType()
                            )

                            .applicationId(
                                    event.applicationId()
                            )

                            .failureReason(
                                    decision.failureReason()
                            )

                            .build();

            /*
                Publish Result
             */
            producer.publish(result);

            log.info(
                    "Credit score completed successfully: {}",
                    event.applicationId()
            );

        } catch (Exception ex) {

            log.error(
                    "Credit scoring failed for application: {}",
                    event.applicationId(),
                    ex
            );

            RatingResultDto result =
                    RatingResultDto.builder()

                            .status("FAILED")

                            .score(300)

                            .cardType(null)

                            .applicationId(
                                    event.applicationId()
                            )

                            .failureReason(
                                    ex.getMessage()
                            )

                            .build();

            producer.publish(result);
        }
    }
}