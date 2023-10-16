package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;

@Entity()
@Table(name = "energy_source")
public class EnergySource {
    @ManyToOne
    @JoinColumn(name = "network_id", referencedColumnName = "id")
    private EnergyNetwork energyNetwork;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private Provider provider;


    private String energyType; // Typ Energiequelle ("Solar", "Wind", "Conventional")
    private double maxCapacity; // Maximale Kapazität der Energiequelle in MWh
    private double currentStorage; // Aktueller Speicherstand der Energiequelle in MWh

    public EnergySource() {
    }

    // Getter und Setter für die ID der Energiequelle
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter und Setter für den Typ der Energiequelle
    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    // Getter und Setter für die maximale Kapazität der Energiequelle
    public double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Getter und Setter für den aktuellen Speicherstand der Energiequelle
    public double getCurrentStorage() {
        return currentStorage;
    }

    public void setCurrentStorage(double currentStorage) {
        this.currentStorage = currentStorage;
    }

    // Getter und Setter für das Energie-Netzwerk, zu dem die Energiequelle gehört
    public EnergyNetwork getEnergyNetwork() {
        return energyNetwork;
    }

    public void setEnergyNetwork(EnergyNetwork energyNetwork) {
        this.energyNetwork = energyNetwork;
    }


    // Anbieter (Provider) des Energie-Netzwerks
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}

