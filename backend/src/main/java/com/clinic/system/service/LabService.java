package com.clinic.system.service;

import com.clinic.system.dto.LabResultRequest;
import com.clinic.system.entity.LabOrder;
import com.clinic.system.entity.LabResult;
import com.clinic.system.enums.AppointmentStatus;
import com.clinic.system.enums.LabOrderStatus;
import com.clinic.system.repository.LabOrderRepository;
import com.clinic.system.repository.LabResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LabService {

    private final LabOrderRepository labOrderRepository;
    private final LabResultRepository labResultRepository;

    public List<LabOrder> pendingOrders() {
        return labOrderRepository.findByStatus(LabOrderStatus.PENDING);
    }

    @Transactional
    public LabResult recordResult(LabResultRequest request) {
        LabOrder order = labOrderRepository.findById(request.labOrderId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Lab order not found"));

        LabResult result = labResultRepository.findByLabOrderId(order.getId()).orElseGet(LabResult::new);
        result.setLabOrder(order);
        result.setResultNotes(request.resultNotes());
        result.setReportUrl(request.reportUrl());
        result.setStatus(request.status());

        order.setStatus(request.status());
        if (request.status() == LabOrderStatus.COMPLETED || request.status() == LabOrderStatus.VERIFIED) {
            order.getConsultation().getAppointment().setStatus(AppointmentStatus.CONSULTING);
        }
        labOrderRepository.save(order);

        return labResultRepository.save(result);
    }
}
