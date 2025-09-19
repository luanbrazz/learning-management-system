package com.lbraz.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record StudentRegistrationDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        @Past
        LocalDate birthDate,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String zipCode,
        @NotBlank
        String addressLine1,
        String addressLine2,
        @NotBlank
        String city,
        @NotBlank
        String state
) {
}