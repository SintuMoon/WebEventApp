package com.webeventapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webeventapp.backend.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
