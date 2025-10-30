package com.webeventapp.backend.provider.dto;

import com.webeventapp.backend.provider.model.PriceTier;
import com.webeventapp.backend.provider.model.ProviderCategory;
import java.time.Instant;
import java.util.List;

public record ProviderResponse(
        Long id,
        String name,
        String slug,
        ProviderCategory category,
        String city,
        String region,
        String country,
        PriceTier priceTier,
        String priceLabel,
        String shortDescription,
        List<String> images,
        String instagramUrl,
        String videoUrl,
        String email,
        String phone,
        boolean featured,
        Instant createdAt,
        Instant updatedAt
) {
}
