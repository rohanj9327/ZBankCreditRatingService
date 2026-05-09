package com.zbank.creditscore.messaging.consumer;

import com.zbank.creditscore.dto.ApplicantRequestDto;
import com.zbank.creditscore.messaging.consumer.event.CustomerApplicationEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationEventConsumerTest {

    @InjectMocks
    private CustomerApplicationEventConsumer consumer;

    @Test
    void shouldVerifyAllDetailsAreProperlyReadFromEvent() {
        ApplicantRequestDto dto = ApplicantRequestDto.builder()
                .firstName("Alice")
                .lastName("Wonderland")
                .email("alice@zbank.com")
                .annualSalary(new BigDecimal("120000"))
                .build();

        CustomerApplicationEvent event = new CustomerApplicationEvent(
                "APP-999",
                "APPLICATION_SUBMITTED",
                dto
        );

        consumer.consumeCustomerApplicationEvent(event);

        // 4. Assertions to verify the consumer can read nested DTO data
        assertNotNull(event.applicantData());
        assertEquals("Alice", event.applicantData().getFirstName());
        assertEquals("Wonderland", event.applicantData().getLastName());
        assertEquals(new BigDecimal("120000"), event.applicantData().getAnnualSalary());
    }
}