package com.example.eventmanager.repository;

import com.example.eventmanager.entity.PaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
}
