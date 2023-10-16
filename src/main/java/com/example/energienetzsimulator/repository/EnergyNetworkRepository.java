package com.example.energienetzsimulator.repository;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyNetworkRepository extends JpaRepository<EnergyNetwork, Long> {
    // Hier können benutzerdefinierte Abfragen hinzugefügt werden, falls erforderlich
}

