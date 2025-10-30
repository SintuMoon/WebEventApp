package com.webeventapp.backend.provider.spec;

import com.webeventapp.backend.provider.model.PriceTier;
import com.webeventapp.backend.provider.model.Provider;
import com.webeventapp.backend.provider.model.ProviderCategory;
import org.springframework.data.jpa.domain.Specification;

public final class ProviderSpecifications {

    private ProviderSpecifications() {
    }

    public static Specification<Provider> hasCategory(ProviderCategory category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<Provider> locatedInCity(String city) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("city")), city.toLowerCase());
    }

    public static Specification<Provider> locatedInRegion(String region) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("region")), region.toLowerCase());
    }

    public static Specification<Provider> hasPriceTier(PriceTier priceTier) {
        return (root, query, cb) -> cb.equal(root.get("priceTier"), priceTier);
    }

    public static Specification<Provider> isFeatured(boolean featured) {
        return (root, query, cb) -> cb.equal(root.get("featured"), featured);
    }
}
