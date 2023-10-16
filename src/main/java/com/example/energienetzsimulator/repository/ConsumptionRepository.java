package com.example.energienetzsimulator.repository;

import com.example.energienetzsimulator.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    // Benutzerdefinierte Abfrage, um den aktuellen Verbrauch für einen Verbraucher abzurufen
    @Query("SELECT SUM(c.value) FROM Consumption c WHERE c.consumer.id = :consumerId")
    default Double findCurrentConsumptionForConsumer(@Param("consumerId") Long consumerId) {
        return null;
    }
}

