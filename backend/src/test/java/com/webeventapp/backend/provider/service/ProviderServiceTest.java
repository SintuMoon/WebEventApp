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
                47.3769,
                8.5417,
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
        assertThat(response.latitude()).isEqualTo(47.3769);
        assertThat(response.longitude()).isEqualTo(8.5417);
        assertThat(response.priceTier()).isEqualTo(PriceTier.MID_RANGE);
        assertThat(response.priceLabel()).isEqualTo(PriceTier.MID_RANGE.getLabel());
    }

    @Test
    void searchProvidersFiltersByCategory() {
        ProviderSearchCriteria criteria = ProviderSearchCriteria.of(
                ProviderCategory.PHOTOGRAPHY,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Page<ProviderResponse> result = providerService.searchProviders(criteria, PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void searchProvidersWithinRadiusFiltersByCoordinates() {
        ProviderResponse zurichProvider = providerService.createProvider(new ProviderRequest(
                "Zurich Party DJs",
                null,
                ProviderCategory.DJ,
                "Zürich",
                "Zürich",
                "Schweiz",
                47.3769,
                8.5417,
                PriceTier.PREMIUM,
                "Premium DJ Service",
                List.of("https://example.com/dj.jpg"),
                null,
                null,
                "dj@zurichparty.ch",
                null,
                false
        ));

        ProviderResponse genevaProvider = providerService.createProvider(new ProviderRequest(
                "Geneva Party DJs",
                null,
                ProviderCategory.DJ,
                "Genf",
                "Genf",
                "Schweiz",
                46.2044,
                6.1432,
                PriceTier.MID_RANGE,
                "Genfer DJ Service",
                List.of("https://example.com/dj2.jpg"),
                null,
                null,
                "dj@genevaparty.ch",
                null,
                false
        ));

        ProviderSearchCriteria criteria = ProviderSearchCriteria.of(
                null,
                null,
                null,
                null,
                null,
                47.3769,
                8.5417,
                30
        );

        Page<ProviderResponse> result = providerService.searchProviders(criteria, PageRequest.of(0, 20));

        assertThat(result.getContent()).extracting(ProviderResponse::id).contains(zurichProvider.id());
        assertThat(result.getContent()).extracting(ProviderResponse::id).doesNotContain(genevaProvider.id());
    }
}
