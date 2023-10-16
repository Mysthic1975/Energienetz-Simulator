package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;

import java.util.List;

@SuppressWarnings("ALL")
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

    public Consumer() {
    }

    /**
     *
     * @return ID des Verbrauchers
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id ID des Verbrauchers
     */
    public void setId(Long id) {
        this.id = id;
    }

    // Vorname des Verbrauchers
    public String getFirstName() {
        return firstName;
    }

    /**
     * Einfach mal was hingeschrieben
     *
     * @param firstName Vornamen des Verbrauchers
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Nachname des Verbrauchers
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Kundennummer des Verbrauchers
    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    // Erwarteter jährlicher Energieverbrauch des Verbrauchers
    public Double getExpectedAnnualUsage() {
        return expectedAnnualUsage;
    }

    public void setExpectedAnnualUsage(Double expectedAnnualUsage) {
        this.expectedAnnualUsage = expectedAnnualUsage;
    }

    // Energie-Netzwerk, dem der Verbraucher zugeordnet ist
    public EnergyNetwork getEnergyNetwork() {
        return energyNetwork;
    }

    public void setEnergyNetwork(EnergyNetwork energyNetwork) {
        this.energyNetwork = energyNetwork;
    }
}

