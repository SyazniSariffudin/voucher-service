package com.example.voucher.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@Tag(name = "Health", description = "To check status of service")
public class HealthController {

    @GetMapping(value = "/health")
    public String health() {
        return "Service is Up & Running. " + Instant.now().toString();
    }
}
