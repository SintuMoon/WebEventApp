package com.webeventapp.backend.common;

import com.webeventapp.backend.provider.repository.ProviderRepository;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class SlugService {

    private static final Pattern NONLATIN = Pattern.compile("[^\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\s]");

    private final ProviderRepository providerRepository;

    public SlugService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public String generateUniqueSlug(String input) {
        String baseSlug = toSlug(input);
        String candidate = baseSlug;
        int counter = 1;
        while (providerRepository.existsBySlug(candidate)) {
            candidate = baseSlug + "-" + counter++;
        }
        return candidate;
    }

    private String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input.trim().toLowerCase(Locale.ROOT)).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = slug.replaceAll("-+", "-");
        if (slug.endsWith("-")) {
            slug = slug.substring(0, slug.length() - 1);
        }
        return slug;
    }
}
