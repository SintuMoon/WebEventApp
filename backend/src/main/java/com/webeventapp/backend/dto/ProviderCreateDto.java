package com.webeventapp.backend.dto;

import java.util.List;

public record ProviderCreateDto(
        String name,
        String category,
        String location,
        String description,
        String email,
        String phone,
        String instagram,
        String whatsapp,
        String website,
        List<ProviderMediaDto> media
) {
}
