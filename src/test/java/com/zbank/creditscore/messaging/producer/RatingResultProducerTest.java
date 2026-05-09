package com.zbank.creditscore.messaging.producer;

import com.zbank.creditscore.dto.RatingResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RatingResultProducerTest {

    @Mock
    private KafkaTemplate<
            String,
            RatingResultDto
            > kafkaTemplate;

    @InjectMocks
    private RatingResultProducer producer;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                producer,
                "topicName",
                "test-rating-topic"
        );
    }

    @Test
    void shouldPublishRatingResultSuccessfully() {

        RatingResultDto dto =
                RatingResultDto.builder()
                        .status("PASSED")
                        .score(750)
                        .cardType("PLATINUM")
                        .applicationId(1001L)
                        .failureReason(null)
                        .build();

        /*
            Call Producer
         */
        producer.publish(dto);

        /*
            Capture Event
         */
        ArgumentCaptor<RatingResultDto>
                dtoCaptor =
                ArgumentCaptor.forClass(
                        RatingResultDto.class
                );

        verify(kafkaTemplate)
                .send(
                        eq("test-rating-topic"),
                        anyString(),
                        dtoCaptor.capture()
                );

        RatingResultDto capturedDto =
                dtoCaptor.getValue();

        /*
            Assertions
         */
        assertEquals(
                "PASSED",
                capturedDto.status()
        );

        assertEquals(
                750,
                capturedDto.score()
        );

        assertEquals(
                "PLATINUM",
                capturedDto.cardType()
        );

        assertEquals(
                1001L,
                capturedDto.applicationId()
        );

        assertEquals(
                null,
                capturedDto.failureReason()
        );
    }

    @Test
    void shouldPublishFailedRatingResult() {

        RatingResultDto dto =
                RatingResultDto.builder()
                        .status("FAILED")
                        .score(300)
                        .cardType(null)
                        .applicationId(2002L)
                        .failureReason(
                                "Minimum salary criteria failed"
                        )
                        .build();

        producer.publish(dto);

        ArgumentCaptor<RatingResultDto>
                dtoCaptor =
                ArgumentCaptor.forClass(
                        RatingResultDto.class
                );

        verify(kafkaTemplate)
                .send(
                        eq("test-rating-topic"),
                        anyString(),
                        dtoCaptor.capture()
                );

        RatingResultDto capturedDto =
                dtoCaptor.getValue();

        assertEquals(
                "FAILED",
                capturedDto.status()
        );

        assertEquals(
                300,
                capturedDto.score()
        );

        assertEquals(
                2002L,
                capturedDto.applicationId()
        );

        assertEquals(
                "Minimum salary criteria failed",
                capturedDto.failureReason()
        );
    }
}