package com.webeventapp.backend.provider.dto;

import com.webeventapp.backend.provider.model.PriceTier;
import com.webeventapp.backend.provider.model.ProviderCategory;
import java.util.Optional;

public record ProviderSearchCriteria(
        Optional<ProviderCategory> category,
        Optional<String> city,
        Optional<String> region,
        Optional<PriceTier> priceTier,
        Optional<Boolean> featured
) {
    public static ProviderSearchCriteria of(
            ProviderCategory category,
            String city,
            String region,
            PriceTier priceTier,
            Boolean featured
    ) {
        return new ProviderSearchCriteria(
                Optional.ofNullable(category),
                Optional.ofNullable(city),
                Optional.ofNullable(region),
                Optional.ofNullable(priceTier),
                Optional.ofNullable(featured)
        );
    }
}
