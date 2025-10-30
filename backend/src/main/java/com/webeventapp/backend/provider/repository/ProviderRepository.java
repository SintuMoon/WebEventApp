package com.webeventapp.backend.provider.repository;

import com.webeventapp.backend.provider.model.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProviderRepository extends JpaRepository<Provider, Long>, JpaSpecificationExecutor<Provider> {

    Optional<Provider> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
