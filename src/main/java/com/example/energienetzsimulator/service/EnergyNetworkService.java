package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.repository.EnergyNetworkRepository;
import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.repository.EnergySourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyNetworkService {

    private final EnergyNetworkRepository energyNetworkRepository;
    private final EnergySourceRepository energySourceRepository;

    @Autowired
    public EnergyNetworkService(
            EnergyNetworkRepository energyNetworkRepository,
            EnergySourceRepository energySourceRepository) {
        this.energyNetworkRepository = energyNetworkRepository;
        this.energySourceRepository = energySourceRepository;
    }

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
        EnergyNetwork existingEnergyNetwork = energyNetworkRepository.findById(id).orElse(null);
        if (existingEnergyNetwork != null) {
            existingEnergyNetwork.setName(updatedEnergyNetwork.getName());

            return energyNetworkRepository.save(existingEnergyNetwork);
        }
        return null;
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




