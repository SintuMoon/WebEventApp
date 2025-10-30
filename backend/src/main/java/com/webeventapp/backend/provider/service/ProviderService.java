package com.webeventapp.backend.provider.service;

import com.webeventapp.backend.common.SlugService;
import com.webeventapp.backend.exception.ResourceNotFoundException;
import com.webeventapp.backend.provider.dto.ProviderRequest;
import com.webeventapp.backend.provider.dto.ProviderResponse;
import com.webeventapp.backend.provider.dto.ProviderSearchCriteria;
import com.webeventapp.backend.provider.model.Provider;
import com.webeventapp.backend.provider.repository.ProviderRepository;
import com.webeventapp.backend.provider.spec.ProviderSpecifications;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final SlugService slugService;

    public ProviderService(ProviderRepository providerRepository, SlugService slugService) {
        this.providerRepository = providerRepository;
        this.slugService = slugService;
    }

    public Page<ProviderResponse> searchProviders(ProviderSearchCriteria criteria, Pageable pageable) {
        Specification<Provider> spec = Specification.where(null);
        if (criteria.category().isPresent()) {
            spec = spec.and(ProviderSpecifications.hasCategory(criteria.category().get()));
        }
        if (criteria.city().isPresent()) {
            spec = spec.and(ProviderSpecifications.locatedInCity(criteria.city().get()));
        }
        if (criteria.region().isPresent()) {
            spec = spec.and(ProviderSpecifications.locatedInRegion(criteria.region().get()));
        }
        if (criteria.priceTier().isPresent()) {
            spec = spec.and(ProviderSpecifications.hasPriceTier(criteria.priceTier().get()));
        }
        if (criteria.featured().isPresent()) {
            spec = spec.and(ProviderSpecifications.isFeatured(criteria.featured().get()));
        }
        if (criteria.hasCompleteLocationRadius()) {
            spec = spec.and(ProviderSpecifications.withinRadius(
                    criteria.latitude().orElseThrow(),
                    criteria.longitude().orElseThrow(),
                    criteria.radiusKm().orElseThrow()
            ));
        }

        Page<Provider> page = providerRepository.findAll(spec, pageable);
        List<ProviderResponse> responses = page.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }

    public ProviderResponse getBySlug(String slug) {
        Provider provider = providerRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        return toResponse(provider);
    }

    public ProviderResponse createProvider(ProviderRequest request) {
        Provider provider = new Provider();
        applyRequest(provider, request, true);
        Provider saved = providerRepository.save(provider);
        return toResponse(saved);
    }

    public ProviderResponse updateProvider(Long id, ProviderRequest request) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
        applyRequest(provider, request, false);
        Provider saved = providerRepository.save(provider);
        return toResponse(saved);
    }

    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Provider not found");
        }
        providerRepository.deleteById(id);
    }

    private void applyRequest(Provider provider, ProviderRequest request, boolean creating) {
        provider.setName(request.name());
        String slug = Optional.ofNullable(request.slug())
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .orElseGet(() -> creating ? slugService.generateUniqueSlug(request.name()) : provider.getSlug());
        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Slug cannot be empty");
        }
        String existingSlug = provider.getSlug();
        if (!Objects.equals(slug, existingSlug)) {
            if (providerRepository.existsBySlug(slug)) {
                throw new IllegalArgumentException("Slug already in use");
            }
            provider.setSlug(slug);
        }
        provider.setCategory(request.category());
        provider.setCity(request.city());
        provider.setRegion(request.region());
        provider.setCountry(request.country());
        validateCoordinates(request.latitude(), request.longitude());
        provider.setLatitude(request.latitude());
        provider.setLongitude(request.longitude());
        provider.setPriceTier(request.priceTier());
        provider.setShortDescription(request.shortDescription());
        List<String> images = request.images() == null
                ? Collections.emptyList()
                : request.images().stream()
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .limit(6)
                        .collect(Collectors.toList());
        provider.setImages(images);
        provider.setInstagramUrl(request.instagramUrl());
        provider.setVideoUrl(request.videoUrl());
        provider.setEmail(request.email());
        provider.setPhone(request.phone());
        provider.setFeatured(request.featured());
    }

    private ProviderResponse toResponse(Provider provider) {
        return new ProviderResponse(
                provider.getId(),
                provider.getName(),
                provider.getSlug(),
                provider.getCategory(),
                provider.getCity(),
                provider.getRegion(),
                provider.getCountry(),
                provider.getLatitude(),
                provider.getLongitude(),
                provider.getPriceTier(),
                provider.getPriceTier().getLabel(),
                provider.getShortDescription(),
                List.copyOf(provider.getImages()),
                provider.getInstagramUrl(),
                provider.getVideoUrl(),
                provider.getEmail(),
                provider.getPhone(),
                provider.isFeatured(),
                provider.getCreatedAt(),
                provider.getUpdatedAt()
        );
    }

    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude == null && longitude == null) {
            return;
        }
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Both latitude and longitude must be provided together");
        }
    }
}
