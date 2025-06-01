package com.example.eventmanager.repository;

import com.example.eventmanager.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
