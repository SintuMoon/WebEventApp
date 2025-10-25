package com.webeventapp.backend.service;

import java.util.List;

import com.webeventapp.backend.dto.ProviderCreateDto;
import com.webeventapp.backend.dto.ProviderDto;
import com.webeventapp.backend.entity.ProviderClick.ClickType;

public interface ProviderService {

    List<ProviderDto> getProviders();

    ProviderDto getProvider(Long id);

    ProviderDto createProvider(ProviderCreateDto dto);

    void recordClick(Long providerId, ClickType type);
}
