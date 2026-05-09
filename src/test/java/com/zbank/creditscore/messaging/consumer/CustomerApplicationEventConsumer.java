package com.zbank.creditscore.messaging.consumer;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import com.zbank.creditscore.dto.RatingResultDto;
import com.zbank.creditscore.messaging.consumer.event.CustomerApplicationEvent;
import com.zbank.creditscore.messaging.producer.RatingResultProducer;
import com.zbank.creditscore.service.ApplicantValidationService;
import com.zbank.creditscore.service.CreditDecision;
import com.zbank.creditscore.service.CreditScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationEventConsumerTest {

    @InjectMocks
    private CustomerApplicationEventConsumer consumer;

    @Mock
    private ApplicantValidationService validationService;

    @Mock
    private CreditScoreService creditScoreService;

    @Mock
    private RatingResultProducer producer;

    @Test
    void shouldProcessCustomerApplicationSuccessfully() {

        ApplicantRequestDto dto =
                ApplicantRequestDto.builder()
                        .firstName("Alice")
                        .lastName("Wonderland")
                        .email("alice@zbank.com")
                        .mobile("9876543210")
                        .dob(LocalDate.of(1998, 5, 10))
                        .employmentType("SALARIED")
                        .annualSalary(new BigDecimal("120000"))
                        .existingCreditCards(2)
                        .build();

        CustomerApplicationEvent event =
                CustomerApplicationEvent.builder()
                        .applicationId(1001L)
                        .eventType("APPLICATION_SUBMITTED")
                        .applicantData(dto)
                        .build();

        CreditDecision decision =
                new CreditDecision(
                        650,
                        "PASSED",
                        "GOLD",
                        null
                );

        when(
                creditScoreService.calculate(any())
        ).thenReturn(decision);

        consumer.consumeCustomerApplicationEvent(event);

        verify(validationService, times(1))
                .validate(any(ApplicantRequestDto.class));

        verify(creditScoreService, times(1))
                .calculate(any(ApplicantRequestDto.class));

        verify(producer, times(1))
                .publish(any(RatingResultDto.class));
    }
}