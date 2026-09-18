package com.clinic.system.controller;

import com.clinic.system.dto.TriageRequest;
import com.clinic.system.entity.TriageVital;
import com.clinic.system.service.TriageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/triage")
@RequiredArgsConstructor
public class TriageController {

    private final TriageService triageService;

    @PostMapping("/vitals")
    @ResponseStatus(HttpStatus.CREATED)
    public TriageVital recordVitals(@Valid @RequestBody TriageRequest request) {
        return triageService.recordVitals(request);
    }
}
