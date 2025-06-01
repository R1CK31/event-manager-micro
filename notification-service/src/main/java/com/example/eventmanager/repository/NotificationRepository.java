package com.example.eventmanager.repository;

import com.example.eventmanager.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
