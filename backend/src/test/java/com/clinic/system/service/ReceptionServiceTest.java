package com.clinic.system.service;

import com.clinic.system.dto.PatientRequest;
import com.clinic.system.entity.Patient;
import com.clinic.system.enums.Gender;
import com.clinic.system.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceptionServiceTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TriageVitalRepository triageVitalRepository;
    @Mock
    private ConsultationRepository consultationRepository;
    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private ReceptionService receptionService;

    @Test
    void registerPatientGeneratesFileNumberAndPersistsData() {
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PatientRequest request = new PatientRequest("Jane Doe", 31, Gender.FEMALE, "0711111111", "Dar es Salaam", "0722222222");
        Patient saved = receptionService.registerPatient(request);

        assertThat(saved.getFileNumber()).startsWith("CLN-");
        assertThat(saved.getFullName()).isEqualTo("Jane Doe");
        assertThat(saved.getPhone()).isEqualTo("0711111111");

        ArgumentCaptor<Patient> captor = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(captor.capture());
        assertThat(captor.getValue().getEmergencyContact()).isEqualTo("0722222222");
    }
}
