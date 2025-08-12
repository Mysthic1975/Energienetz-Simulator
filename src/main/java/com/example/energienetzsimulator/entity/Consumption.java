package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}
