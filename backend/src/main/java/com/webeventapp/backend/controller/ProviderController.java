package com.webeventapp.backend.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.webeventapp.backend.dto.ProviderCreateDto;
import com.webeventapp.backend.dto.ProviderDto;
import com.webeventapp.backend.entity.ProviderClick.ClickType;
import com.webeventapp.backend.service.ProviderService;

@RestController
@RequestMapping("/api/providers")
@CrossOrigin(origins = "*")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<ProviderDto> getProviders() {
        return providerService.getProviders();
    }

    @GetMapping("/{id}")
    public ProviderDto getProvider(@PathVariable Long id) {
        return providerService.getProvider(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderDto createProvider(@RequestBody ProviderCreateDto dto) {
        return providerService.createProvider(dto);
    }

    @PostMapping("/{id}/clicks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerClick(@PathVariable Long id, @RequestBody ClickRequest request) {
        if (request == null || request.type() == null || request.type().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Click type is required");
        }
        providerService.recordClick(id, parseClickType(request.type()));
    }

    private ClickType parseClickType(String rawType) {
        try {
            return ClickType.valueOf(rawType.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported click type: " + rawType);
        }
    }

    public record ClickRequest(String type) { }
}
