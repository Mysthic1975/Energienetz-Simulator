package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.Provider;
import com.example.energienetzsimulator.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

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
        return providerRepository.findById(id).map(existingProvider -> {
            existingProvider.setFirstName(updatedProvider.getFirstName());
            existingProvider.setLastName(updatedProvider.getLastName());
            existingProvider.setProviderNumber(updatedProvider.getProviderNumber());
            return providerRepository.save(existingProvider);
        }).orElse(null);
    }

    // Methode zum Löschen eines Energieanbieters anhand seiner ID
    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }
}
