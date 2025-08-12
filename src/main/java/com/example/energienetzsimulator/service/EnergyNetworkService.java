package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.repository.EnergyNetworkRepository;
import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.repository.EnergySourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyNetworkService {

    private final EnergyNetworkRepository energyNetworkRepository;
    private final EnergySourceRepository energySourceRepository;

    public List<EnergyNetwork> getAllEnergyNetworks() {
        return energyNetworkRepository.findAll();
    }

    public EnergySource assignEnergySourceToNetwork(Long energySourceId, Long networkId) {
        EnergySource energySource = energySourceRepository.findById(energySourceId).orElse(null);
        EnergyNetwork energyNetwork = energyNetworkRepository.findById(networkId).orElse(null);

        if (energySource != null && energyNetwork != null) {
            energySource.setEnergyNetwork(energyNetwork);
            return energySourceRepository.save(energySource);
        }

        return null;
    }

    public EnergyNetwork getEnergyNetworkById(Long id) {
        return energyNetworkRepository.findById(id).orElse(null);
    }

    public EnergyNetwork createEnergyNetwork(EnergyNetwork energyNetwork) {
        return energyNetworkRepository.save(energyNetwork);
    }

    public double calculateTotalEnergyInNetwork(Long networkId) {
        List<EnergySource> energySourcesInNetwork = energySourceRepository.findByEnergyNetworkId(networkId);

        return energySourcesInNetwork.stream()
                .mapToDouble(EnergySource::getCurrentStorage)
                .sum();
    }

    public EnergyNetwork updateEnergyNetwork(Long id, EnergyNetwork updatedEnergyNetwork) {
        return energyNetworkRepository.findById(id).map(existingEnergyNetwork -> {
            existingEnergyNetwork.setName(updatedEnergyNetwork.getName());
            return energyNetworkRepository.save(existingEnergyNetwork);
        }).orElse(null);
    }

    public void deleteEnergyNetwork(Long networkId) {
        // Zuerst alle Energiequellen dieses Netzwerks finden
        List<EnergySource> energySourcesInNetwork = energySourceRepository.findByEnergyNetworkId(networkId);

        // Alle Energiequellen auf einmal löschen
        energySourceRepository.deleteAll(energySourcesInNetwork);

        // Schließlich das Energie-Netzwerk löschen
        energyNetworkRepository.deleteById(networkId);
    }

    public double calculateTotalCurrentStorageInNetwork(Long networkId) {
        List<EnergySource> energySourcesInNetwork = energySourceRepository.findByEnergyNetworkId(networkId);

        return energySourcesInNetwork.stream()
                .mapToDouble(EnergySource::getCurrentStorage)
                .sum();
    }
}
