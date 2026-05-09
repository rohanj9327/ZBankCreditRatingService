package com.zbank.creditscore.messaging.producer;

import com.zbank.creditscore.dto.RatingResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingResultProducer {

    private final KafkaTemplate<
            String,
            RatingResultDto
            > kafkaTemplate;

    public void publish(
            RatingResultDto dto
    ) {

        kafkaTemplate.send(
                "ratingresult",
                dto.applicationId().toString(),
                dto
        );
    }
}