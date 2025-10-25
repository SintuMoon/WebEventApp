package com.webeventapp.backend.dto;

import java.util.List;
import java.util.Map;

public record ProviderDto(
        Long id,
        String name,
        String category,
        String location,
        String description,
        String email,
        String phone,
        String instagram,
        String whatsapp,
        String website,
        List<ProviderMediaDto> media,
        long totalClicks,
        Map<String, Long> clicksByType
) {
}
