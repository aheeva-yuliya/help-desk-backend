package com.javamaster.repository;

import com.javamaster.entity.Urgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Urgency, Integer> {
    Urgency findByUrgency(String urgency);
}
