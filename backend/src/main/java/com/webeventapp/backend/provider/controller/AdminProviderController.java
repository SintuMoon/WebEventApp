package com.webeventapp.backend.provider.controller;

import com.webeventapp.backend.provider.dto.ProviderRequest;
import com.webeventapp.backend.provider.dto.ProviderResponse;
import com.webeventapp.backend.provider.dto.ProviderSearchCriteria;
import com.webeventapp.backend.provider.model.PriceTier;
import com.webeventapp.backend.provider.model.ProviderCategory;
import com.webeventapp.backend.provider.service.ProviderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/providers")
@Validated
public class AdminProviderController {

    private final ProviderService providerService;

    public AdminProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public Page<ProviderResponse> list(
            @RequestParam(value = "category", required = false) ProviderCategory category,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "priceTier", required = false) PriceTier priceTier,
            @RequestParam(value = "featured", required = false) Boolean featured,
            @RequestParam(value = "latitude", required = false) @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") Double latitude,
            @RequestParam(value = "longitude", required = false) @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") Double longitude,
            @RequestParam(value = "radiusKm", required = false) @Min(1) @Max(500) Integer radiusKm,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        boolean anyLocationParam = latitude != null || longitude != null || radiusKm != null;
        if (anyLocationParam && !(latitude != null && longitude != null && radiusKm != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "latitude, longitude, and radiusKm must be provided together");
        }

        Pageable pageable = PageRequest.of(page, size);
        ProviderSearchCriteria criteria = ProviderSearchCriteria.of(
                category,
                city,
                region,
                priceTier,
                featured,
                latitude,
                longitude,
                radiusKm
        );
        return providerService.searchProviders(criteria, pageable);
    }

    @PostMapping
    public ProviderResponse create(@Valid @RequestBody ProviderRequest request) {
        return providerService.createProvider(request);
    }

    @PutMapping("/{id}")
    public ProviderResponse update(@PathVariable Long id, @Valid @RequestBody ProviderRequest request) {
        return providerService.updateProvider(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        providerService.deleteProvider(id);
    }
}
