package com.webeventapp.backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.webeventapp.backend.dto.ProviderCreateDto;
import com.webeventapp.backend.dto.ProviderDto;
import com.webeventapp.backend.dto.ProviderMediaDto;
import com.webeventapp.backend.entity.Provider;
import com.webeventapp.backend.entity.ProviderClick;
import com.webeventapp.backend.entity.ProviderMedia;
import com.webeventapp.backend.entity.ProviderClick.ClickType;
import com.webeventapp.backend.repository.ProviderClickRepository;
import com.webeventapp.backend.repository.ProviderRepository;

import org.springframework.http.HttpStatus;

@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderClickRepository providerClickRepository;

    public ProviderServiceImpl(
            ProviderRepository providerRepository,
            ProviderClickRepository providerClickRepository
    ) {
        this.providerRepository = providerRepository;
        this.providerClickRepository = providerClickRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProviderDto> getProviders() {
        return providerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Provider::getName, Comparator.nullsLast(String::compareToIgnoreCase)))
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProviderDto getProvider(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));
        return toDto(provider);
    }

    @Override
    public ProviderDto createProvider(ProviderCreateDto dto) {
        Provider provider = new Provider.Builder()
                .name(dto.name())
                .category(dto.category())
                .location(dto.location())
                .description(dto.description())
                .email(dto.email())
                .phone(dto.phone())
                .instagram(dto.instagram())
                .whatsapp(dto.whatsapp())
                .website(dto.website())
                .build();

        if (dto.media() != null) {
            dto.media().stream()
                    .filter(mediaDto -> mediaDto != null && mediaDto.url() != null && !mediaDto.url().isBlank())
                    .forEach(mediaDto -> provider.addMedia(new ProviderMedia(mediaDto.url(), mediaDto.sortOrder())));
        }

        Provider saved = providerRepository.save(provider);
        return toDto(saved);
    }

    @Override
    public void recordClick(Long providerId, ClickType type) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));

        ProviderClick click = new ProviderClick(type);
        click.setProvider(provider);
        providerClickRepository.save(click);
    }

    private ProviderDto toDto(Provider provider) {
        List<ProviderMediaDto> media = provider.getMediaList()
                .stream()
                .sorted(Comparator.comparing(ProviderMedia::getSortOrder, Comparator.nullsLast(Integer::compareTo)))
                .map(mediaEntity -> new ProviderMediaDto(
                        mediaEntity.getId(),
                        mediaEntity.getUrl(),
                        mediaEntity.getSortOrder()
                ))
                .toList();

        Map<String, Long> clickStats = provider.getClickList()
                .stream()
                .collect(Collectors.groupingBy(
                        click -> click.getType().toLowerCase(Locale.ROOT),
                        Collectors.counting()
                ));

        long totalClicks = clickStats.values().stream().mapToLong(Long::longValue).sum();

        return new ProviderDto(
                provider.getId(),
                provider.getName(),
                provider.getCategory(),
                provider.getLocation(),
                provider.getDescription(),
                provider.getEmail(),
                provider.getPhone(),
                provider.getInstagram(),
                provider.getWhatsapp(),
                provider.getWebsite(),
                media,
                totalClicks,
                clickStats
        );
    }
}
