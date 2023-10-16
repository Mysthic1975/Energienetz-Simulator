package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.Provider;
import com.example.energienetzsimulator.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    // Methode zur Rückgabe aller Energieanbieter
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    // Methode zur Rückgabe eines Energieanbieters anhand seiner ID
    public Provider getProviderById(Long id) {
        return providerRepository.findById(id).orElse(null);
    }

    // Methode zum Erstellen eines neuen Energieanbieters
    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    // Methode zur Aktualisierung eines Energieanbieters anhand seiner ID
    public Provider updateProvider(Long id, Provider updatedProvider) {
        Provider existingProvider = providerRepository.findById(id).orElse(null);
        if (existingProvider != null) {
            // Update the fields of the existing operator with the data from updatedProvider
            existingProvider.setFirstName(updatedProvider.getFirstName());
            existingProvider.setLastName(updatedProvider.getLastName());
            existingProvider.setProviderNumber(updatedProvider.getProviderNumber());

            return providerRepository.save(existingProvider);
        }
        return null; // Behandeln des Falls, in dem der Energieanbieter mit der angegebenen ID nicht gefunden wird
    }

    // Methode zum Löschen eines Energieanbieters anhand seiner ID
    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }

    public void assignEnergySourceToProvider(Long energySourceId, Long providerId) {

    }
}
