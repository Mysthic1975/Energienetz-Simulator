package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.Consumption;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.energienetzsimulator.repository.ConsumptionRepository;
import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.entity.EnergySource;

@Service
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

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
        Consumption existingConsumption = consumptionRepository.findById(consumptionId).orElse(null);
        if (existingConsumption != null) {
            existingConsumption.setValue(value);

            return consumptionRepository.save(existingConsumption);
        }
        return null;
    }

    // Methode zum Abrufen des aktuellen Verbrauchs für einen Verbraucher
    public Double getCurrentConsumptionForConsumer(Long consumerId) {

        return 0.0; // Hier einen Dummy-Wert einfügen
    }

    // Methode zum Löschen eines Verbrauchseintrags
    public void deleteConsumption(Long consumptionId) {
        consumptionRepository.deleteById(consumptionId);
    }

}

