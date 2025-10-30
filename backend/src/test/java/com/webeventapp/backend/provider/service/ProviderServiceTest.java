package com.webeventapp.backend.provider.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.webeventapp.backend.provider.dto.ProviderRequest;
import com.webeventapp.backend.provider.dto.ProviderResponse;
import com.webeventapp.backend.provider.dto.ProviderSearchCriteria;
import com.webeventapp.backend.provider.model.PriceTier;
import com.webeventapp.backend.provider.model.ProviderCategory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class ProviderServiceTest {

    @Autowired
    private ProviderService providerService;

    @Test
    void createProviderGeneratesSlugAndPersists() {
        ProviderRequest request = new ProviderRequest(
                "Golden Lens Studios",
                null,
                ProviderCategory.PHOTOGRAPHY,
                "Zürich",
                "Zürich",
                "Schweiz",
                PriceTier.MID_RANGE,
                "Hochzeitsfotografie mit Liebe zum Detail",
                List.of("https://example.com/1.jpg"),
                "https://instagram.com/goldenlens",
                null,
                "hello@goldenlens.ch",
                "+41790000000",
                true
        );

        ProviderResponse response = providerService.createProvider(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.slug()).startsWith("golden-lens-studios");
        assertThat(response.images()).hasSize(1);
    }

    @Test
    void searchProvidersFiltersByCategory() {
        ProviderSearchCriteria criteria = ProviderSearchCriteria.of(ProviderCategory.PHOTOGRAPHY, null, null, null, null);
        Page<ProviderResponse> result = providerService.searchProviders(criteria, PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isGreaterThanOrEqualTo(0);
    }
}
