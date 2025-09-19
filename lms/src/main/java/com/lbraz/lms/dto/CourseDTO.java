package com.lbraz.lms.dto;

import jakarta.validation.constraints.*;

public record CourseDTO(
        @NotBlank
        @Size(min = 3, max = 255)
        String name,

        @NotBlank
        String description,

        @NotNull
        @Min(1)
        @Max(6)
        Integer durationInMonths
) {
}