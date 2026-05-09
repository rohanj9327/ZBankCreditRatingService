package com.zbank.creditscore.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ApplicantRequestDto {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must be 10 digits"
    )
    private String mobile;

    @Past(message = "DOB must be in past")
    private LocalDate dob;

    @NotBlank(message = "Employment type required")
    private String employmentType;

    @Positive(message = "Salary must be positive")
    private BigDecimal annualSalary;

    @Min(value = 0)
    @Max(value = 10)
    private Integer existingCreditCards;

}
