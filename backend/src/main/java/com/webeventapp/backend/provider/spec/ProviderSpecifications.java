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

    public static Specification<Provider> withinRadius(double latitude, double longitude, int radiusKm) {
        double latDistance = radiusKm / 111.0d;
        double minLat = Math.max(-90.0d, latitude - latDistance);
        double maxLat = Math.min(90.0d, latitude + latDistance);

        double lonDegreePerKm = 111.320d * Math.cos(Math.toRadians(latitude));
        if (Math.abs(lonDegreePerKm) < 1e-6) {
            lonDegreePerKm = 111.320d;
        }
        double lonDistance = radiusKm / Math.abs(lonDegreePerKm);
        double minLon = Math.max(-180.0d, longitude - lonDistance);
        double maxLon = Math.min(180.0d, longitude + lonDistance);

        return (root, query, cb) -> cb.and(
                cb.isNotNull(root.get("latitude")),
                cb.isNotNull(root.get("longitude")),
                cb.between(root.get("latitude"), minLat, maxLat),
                cb.between(root.get("longitude"), minLon, maxLon)
        );
    }
}
