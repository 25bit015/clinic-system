package com.clinic.system.controller;

import com.clinic.system.dto.LabResultRequest;
import com.clinic.system.entity.LabOrder;
import com.clinic.system.entity.LabResult;
import com.clinic.system.service.LabService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab")
@RequiredArgsConstructor
public class LabController {

    private final LabService labService;

    @GetMapping("/orders/pending")
    public List<LabOrder> pendingOrders() {
        return labService.pendingOrders();
    }

    @PostMapping("/results")
    @ResponseStatus(HttpStatus.CREATED)
    public LabResult recordResult(@Valid @RequestBody LabResultRequest request) {
        return labService.recordResult(request);
    }
}
