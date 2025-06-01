package com.example.eventmanager.client;

import com.example.eventmanager.dto.VenueDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "venue-service")
public interface VenueServiceClient {

    @GetMapping("/api/venues/{id}")
    Optional<VenueDto> getVenueById(@PathVariable("id") Long id);

}
