package com.example.energienetzsimulator.entity;

import jakarta.persistence.*;

@SuppressWarnings("ALL")
@Entity
public class EnergyNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;




    public EnergyNetwork() {
    }

    // Getter und Setter für alle Felder

    // ID des Energie-Netzwerks
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Name des Energie-Netzwerks
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


