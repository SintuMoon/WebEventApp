package com.webeventapp.backend.provider.controller;

import com.webeventapp.backend.provider.dto.ProviderRequest;
import com.webeventapp.backend.provider.dto.ProviderResponse;
import com.webeventapp.backend.provider.dto.ProviderSearchCriteria;
import com.webeventapp.backend.provider.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/providers")
public class AdminProviderController {

    private final ProviderService providerService;

    public AdminProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public Page<ProviderResponse> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return providerService.searchProviders(
                ProviderSearchCriteria.of(null, null, null, null, null),
                pageable
        );
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
