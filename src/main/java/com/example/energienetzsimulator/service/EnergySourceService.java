package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.entity.Provider;
import com.example.energienetzsimulator.repository.EnergySourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnergySourceService {
    private final EnergySourceRepository energySourceRepository;
    private final EnergyNetworkService energyNetworkService;
    private final ProviderService providerService;

    @Autowired
    public EnergySourceService(EnergySourceRepository energySourceRepository, EnergyNetworkService energyNetworkService, ProviderService providerService) {
        this.energySourceRepository = energySourceRepository;
        this.energyNetworkService = energyNetworkService;
        this.providerService = providerService;
    }

    public List<EnergySource> getAllEnergySources() {
        return energySourceRepository.findAll();
    }

    public EnergySource getEnergySourceById(Long id) {
        return energySourceRepository.findById(id).orElse(null);
    }
    @Transactional
    public void createEnergySource(EnergySource energySource) {
        if (energySource.getEnergyNetwork() != null && energySource.getEnergyNetwork().getId() != null
                && energySource.getProvider() != null && energySource.getProvider().getId() != null) {
            EnergyNetwork energyNetwork = energyNetworkService.getEnergyNetworkById(energySource.getEnergyNetwork().getId());
            Provider provider = providerService.getProviderById(energySource.getProvider().getId());

            if (energyNetwork != null && provider != null) {
                energySource.setEnergyNetwork(energyNetwork);
                energySource.setProvider(provider);
                // Set other fields as needed
                energySourceRepository.save(energySource);
            }
        }
    }

    public EnergySource updateEnergySource(Long id, EnergySource updatedEnergySource) {
        EnergySource existingEnergySource = energySourceRepository.findById(id).orElse(null);
        if (existingEnergySource != null) {
            existingEnergySource.setEnergyType(updatedEnergySource.getEnergyType());
            existingEnergySource.setMaxCapacity(updatedEnergySource.getMaxCapacity());
            existingEnergySource.setCurrentStorage(updatedEnergySource.getCurrentStorage());

            return energySourceRepository.save(existingEnergySource);
        }
        return null;
    }

    public void deleteEnergySource(Long id) {
        energySourceRepository.deleteById(id);
    }

    public EnergySource chargeEnergySource(Long id, double chargeAmount) {
        EnergySource existingEnergySource = energySourceRepository.findById(id).orElse(null);
        if (existingEnergySource != null) {
            double currentStorage = existingEnergySource.getCurrentStorage();
            currentStorage += chargeAmount;
            existingEnergySource.setCurrentStorage(currentStorage);

            return energySourceRepository.save(existingEnergySource);
        }
        return null;
    }
}

