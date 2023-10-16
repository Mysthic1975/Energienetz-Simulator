package com.example.energienetzsimulator.repository;

import com.example.energienetzsimulator.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    // Hier können benutzerdefinierte Abfragen hinzugefügt werden, falls erforderlich
}

