package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Consumer {

    @ManyToOne
    @JoinColumn(name = "network_id")
    private EnergyNetwork energyNetwork;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "expected_annual_usage")
    private Double expectedAnnualUsage;

    @OneToMany(mappedBy = "consumer")
    private List<Consumption> consumptions;
}
