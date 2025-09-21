package com.lbraz.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record StudentRegistrationRequest(
        @NotBlank(message = "validation.firstName.notBlank")
        String firstName,
        @NotBlank(message = "validation.lastName.notBlank")
        String lastName,
        @NotNull(message = "validation.birthDate.notNull")
        @Past(message = "validation.birthDate.past")
        LocalDate birthDate,
        @NotBlank(message = "validation.email.notBlank")
        @Email(message = "validation.email.invalid")
        String email,
        @NotBlank(message = "validation.phoneNumber.notBlank")
        String phoneNumber,
        @NotBlank(message = "validation.zipCode.notBlank")
        String zipCode,
        @NotBlank(message = "validation.addressLine1.notBlank")
        String addressLine1,
        String addressLine2,
        @NotBlank(message = "validation.city.notBlank")
        String city,
        @NotBlank(message = "validation.state.notBlank")
        String state,
        @NotBlank(message = "validation.password.notBlank")
        String password
) {
}