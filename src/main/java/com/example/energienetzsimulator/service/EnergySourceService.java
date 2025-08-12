package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.entity.Provider;
import com.example.energienetzsimulator.repository.EnergySourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergySourceService {
    private final EnergySourceRepository energySourceRepository;
    private final EnergyNetworkService energyNetworkService;
    private final ProviderService providerService;

    public List<EnergySource> getAllEnergySources() {
        return energySourceRepository.findAll();
    }

    public EnergySource getEnergySourceById(Long id) {
        return energySourceRepository.findById(id).orElse(null);
    }

    @Transactional
    public void createEnergySource(EnergySource energySource) {
        if (energySource.getEnergyNetwork() == null || energySource.getEnergyNetwork().getId() == null) {
            return;
        }
        if (energySource.getProvider() == null || energySource.getProvider().getId() == null) {
            return;
        }

        EnergyNetwork energyNetwork = energyNetworkService.getEnergyNetworkById(energySource.getEnergyNetwork().getId());
        Provider provider = providerService.getProviderById(energySource.getProvider().getId());

        if (energyNetwork != null && provider != null) {
            energySource.setEnergyNetwork(energyNetwork);
            energySource.setProvider(provider);
            energySourceRepository.save(energySource);
        }
    }

    public EnergySource updateEnergySource(Long id, EnergySource updatedEnergySource) {
        return energySourceRepository.findById(id).map(existingEnergySource -> {
            existingEnergySource.setEnergyType(updatedEnergySource.getEnergyType());
            existingEnergySource.setMaxCapacity(updatedEnergySource.getMaxCapacity());
            existingEnergySource.setCurrentStorage(updatedEnergySource.getCurrentStorage());
            return energySourceRepository.save(existingEnergySource);
        }).orElse(null);
    }

    public void deleteEnergySource(Long id) {
        energySourceRepository.deleteById(id);
    }

    public EnergySource chargeEnergySource(Long id, double chargeAmount) {
        return energySourceRepository.findById(id).map(existingEnergySource -> {
            double currentStorage = existingEnergySource.getCurrentStorage();
            currentStorage += chargeAmount;
            existingEnergySource.setCurrentStorage(currentStorage);
            return energySourceRepository.save(existingEnergySource);
        }).orElse(null);
    }

    public EnergySource assignProviderToEnergySource(Long energySourceId, Long providerId) {
        EnergySource energySource = getEnergySourceById(energySourceId);
        Provider provider = providerService.getProviderById(providerId);
        if (energySource != null && provider != null) {
            energySource.setProvider(provider);
            return energySourceRepository.save(energySource);
        }
        return null;
    }
}
