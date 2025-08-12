package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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


    private String energyType;
    private double maxCapacity;
    private double currentStorage;
}
