package com.example.energienetzsimulator.repository;

import com.example.energienetzsimulator.entity.EnergySource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnergySourceRepository extends JpaRepository<EnergySource, Long> {

    List<EnergySource> findByEnergyNetworkId(Long networkId);
}
