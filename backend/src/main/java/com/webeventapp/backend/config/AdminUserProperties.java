package com.webeventapp.backend.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "admin.user")
public record AdminUserProperties(
        @NotBlank String username,
        @NotBlank String password
) {
}
