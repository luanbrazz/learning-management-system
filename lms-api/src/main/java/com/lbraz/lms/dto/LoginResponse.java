package com.lbraz.lms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record LoginResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("role")
        String userRole,

        @JsonProperty("full_name")
        String fullName,

        @JsonProperty("user_id")
        UUID userId,

        @JsonProperty("student_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        UUID studentId
) {
}
