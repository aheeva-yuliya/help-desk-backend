package com.javamaster.repositories;

import com.javamaster.entities.Urgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Urgency, Integer> {
    Urgency findByUrgency(String urgency);
}
