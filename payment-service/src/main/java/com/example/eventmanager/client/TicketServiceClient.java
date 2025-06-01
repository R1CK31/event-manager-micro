package com.example.eventmanager.client;

import com.example.eventmanager.dto.TicketDto; // Assuming a TicketDto exists or create one
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "ticket-service")
public interface TicketServiceClient {

    @GetMapping("/api/tickets/{id}")
    Optional<TicketDto> getTicketById(@PathVariable("id") Long id);
}

