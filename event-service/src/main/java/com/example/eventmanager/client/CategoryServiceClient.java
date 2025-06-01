package com.example.eventmanager.client;


import com.example.eventmanager.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "category-service")
public interface CategoryServiceClient {

    @GetMapping("/api/categories/{id}")
    Optional<CategoryDto> getCategoryById(@PathVariable("id") Long id);

}
