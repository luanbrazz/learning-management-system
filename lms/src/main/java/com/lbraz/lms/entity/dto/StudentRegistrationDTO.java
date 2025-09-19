package com.lbraz.lms.entity.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public record StudentRegistrationDTO(
        @NotBlank(message = "O primeiro nome é obrigatório.")
        String firstName,

        @NotBlank(message = "O último nome é obrigatório.")
        String lastName,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve ser no passado.")
        LocalDate birthDate,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        String phoneNumber,

        @NotBlank(message = "O CEP é obrigatório.")
        String zipCode,

        @NotBlank(message = "A rua e número são obrigatórios.")
        String addressLine1,

        String addressLine2,

        @NotBlank(message = "A cidade é obrigatória.")
        String city,

        @NotBlank(message = "O estado é obrigatório.")
        String state
) {
}