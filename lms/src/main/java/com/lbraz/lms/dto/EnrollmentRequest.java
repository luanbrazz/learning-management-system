package com.lbraz.lms.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentRequest(
        @NotNull
        UUID studentId,

        @NotNull
        UUID courseId
) {
}
