package com.clinic.system.repository;

import com.clinic.system.entity.Appointment;
import com.clinic.system.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByPatientIdOrderByAppointmentTimeDesc(Long patientId);
}
