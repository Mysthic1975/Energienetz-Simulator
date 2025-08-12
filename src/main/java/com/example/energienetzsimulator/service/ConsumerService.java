package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    // Methode zur Rückgabe aller Verbraucher
    public List<Consumer> getAllConsumers() {
        return consumerRepository.findAll();
    }

    // Methode zur Rückgabe eines Verbrauchers anhand seiner ID
    public Consumer getConsumerById(Long id) {
        return consumerRepository.findById(id).orElse(null);
    }

    // Methode zum Erstellen eines neuen Verbrauchers
    public Consumer createConsumer(Consumer consumer) {
        return consumerRepository.save(consumer);
    }

    // Methode zur Aktualisierung eines Verbrauchers anhand seiner ID
    public Consumer updateConsumer(Long id, Consumer updatedConsumer) {
        return consumerRepository.findById(id).map(existingConsumer -> {
            existingConsumer.setFirstName(updatedConsumer.getFirstName());
            existingConsumer.setLastName(updatedConsumer.getLastName());
            existingConsumer.setCustomerNumber(updatedConsumer.getCustomerNumber());
            existingConsumer.setExpectedAnnualUsage(updatedConsumer.getExpectedAnnualUsage());
            return consumerRepository.save(existingConsumer);
        }).orElse(null);
    }

    // Methode zum Löschen eines Verbrauchers anhand seiner ID
    public void deleteConsumer(Long id) {
        consumerRepository.deleteById(id);
    }
}
