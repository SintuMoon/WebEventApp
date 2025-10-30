package com.webeventapp.backend.provider.dto;

import com.webeventapp.backend.provider.model.PriceTier;
import com.webeventapp.backend.provider.model.ProviderCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ProviderRequest(
        @NotBlank String name,
        String slug,
        @NotNull ProviderCategory category,
        @NotBlank String city,
        String region,
        @NotBlank String country,
        @NotNull PriceTier priceTier,
        @Size(max = 1024) String shortDescription,
        @Size(max = 6) List<@NotBlank String> images,
        String instagramUrl,
        String videoUrl,
        @NotBlank @Email String email,
        String phone,
        boolean featured
) {
}
