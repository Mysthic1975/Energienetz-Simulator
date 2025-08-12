package com.example.energienetzsimulator.service;

import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.repository.ConsumerRepository;
import com.example.energienetzsimulator.repository.EnergySourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final EnergySourceRepository energySourceRepository;
    private final ConsumerRepository consumerRepository;

    @Transactional
    public void simulateNetworkTick(Long networkId) {
        // 1. Alle Energiequellen und Verbraucher für das gegebene Netzwerk abrufen.
        List<EnergySource> sources = energySourceRepository.findByEnergyNetworkId(networkId);
        // Annahme: Consumer sind nicht direkt mit dem Netzwerk verknüpft, wir nehmen alle.
        // In einer echten Anwendung müsste hier eine Verknüpfung existieren.
        List<Consumer> consumers = consumerRepository.findAll(); // Vereinfachung!

        if (sources.isEmpty() || consumers.isEmpty()) {
            // Nichts zu simulieren, wenn keine Quellen oder Verbraucher da sind.
            return;
        }

        // 2. Gesamten aktuellen Bedarf und verfügbare Energie berechnen.
        // Wir nehmen den Jahresverbrauch / 8760 (Stunden im Jahr) als stündlichen Bedarf.
        double totalDemand = consumers.stream()
                .mapToDouble(consumer -> consumer.getExpectedAnnualUsage() / 8760)
                .sum();

        double totalAvailableStorage = sources.stream()
                .mapToDouble(EnergySource::getCurrentStorage)
                .sum();

        if (totalAvailableStorage <= 0 || totalDemand <= 0) {
            // Nichts zu verteilen.
            return;
        }

        // 3. Den tatsächlichen zu deckenden Bedarf ermitteln (kann nicht mehr als verfügbar sein).
        double demandToCover = Math.min(totalDemand, totalAvailableStorage);

        // 4. Den Bedarf proportional von jeder Energiequelle abziehen.
        for (EnergySource source : sources) {
            if (source.getCurrentStorage() > 0) {
                double proportion = source.getCurrentStorage() / totalAvailableStorage;
                double drawAmount = demandToCover * proportion;

                double newStorage = source.getCurrentStorage() - drawAmount;
                source.setCurrentStorage(Math.max(0, newStorage)); // Sicherstellen, dass der Speicher nicht negativ wird.
                energySourceRepository.save(source);
            }
        }
    }
}
