package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;
import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.entity.EnergySource;

@Entity
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @ManyToOne
    @JoinColumn(name = "energy_source_id")
    private EnergySource energySource;

    @ManyToOne
    @JoinColumn(name = "network_id")
    private EnergyNetwork energyNetwork;

    private Double value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public EnergySource getEnergySource() {
        return energySource;
    }

    public void setEnergySource(EnergySource energySource) {
        this.energySource = energySource;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public EnergyNetwork getEnergyNetwork() {
        return energyNetwork;
    }

    public void setEnergyNetwork(EnergyNetwork energyNetwork) {
        this.energyNetwork = energyNetwork;
    }
}


