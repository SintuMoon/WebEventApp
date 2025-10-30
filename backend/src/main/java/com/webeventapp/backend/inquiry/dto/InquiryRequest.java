package com.webeventapp.backend.inquiry.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record InquiryRequest(
        @NotNull Long providerId,
        @NotBlank String organizerName,
        @NotBlank @Email String email,
        String phone,
        @Size(max = 4000) String message,
        LocalDate eventDate
) {
}
