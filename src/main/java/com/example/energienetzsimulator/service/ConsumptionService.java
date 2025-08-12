package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.Consumption;
import com.example.energienetzsimulator.repository.ConsumptionRepository;
import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.entity.EnergySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;

    // Methode zum Erstellen eines Verbrauchseintrags
    public Consumption createConsumption(Consumer consumer, EnergySource energySource, Double value) {
        Consumption consumption = new Consumption();
        consumption.setConsumer(consumer);
        consumption.setEnergySource(energySource);
        consumption.setValue(value);

        return consumptionRepository.save(consumption);
    }

    // Methode zum Aktualisieren eines Verbrauchseintrags
    public Consumption updateConsumption(Long consumptionId, Double value) {
        return consumptionRepository.findById(consumptionId).map(existingConsumption -> {
            existingConsumption.setValue(value);
            return consumptionRepository.save(existingConsumption);
        }).orElse(null);
    }

    // Methode zum Abrufen des aktuellen Verbrauchs für einen Verbraucher
    public Double getCurrentConsumptionForConsumer(Long consumerId) {
        Double consumption = consumptionRepository.findCurrentConsumptionForConsumer(consumerId);
        return consumption != null ? consumption : 0.0;
    }

    // Methode zum Löschen eines Verbrauchseintrags
    public void deleteConsumption(Long consumptionId) {
        consumptionRepository.deleteById(consumptionId);
    }

}
