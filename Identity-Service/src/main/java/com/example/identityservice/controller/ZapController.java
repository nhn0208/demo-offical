package com.example.identityservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZapController {
    @GetMapping("/")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("Root endpoint for ZAP Spider");
    }
}
