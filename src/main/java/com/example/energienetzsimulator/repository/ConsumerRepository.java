package com.example.energienetzsimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.energienetzsimulator.entity.Consumer;


public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    // Hier können benutzerdefinierte Abfragen hinzugefügt werden, falls erforderlich
}

