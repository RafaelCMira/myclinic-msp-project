package com.myclinic.patient;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
class PatientMapper implements RowMapper<Patient> {

    static PatientDTO toDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getBirthDate(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getPhone()
        );
    }

    static List<PatientDTO> toDTO(List<Patient> patients) {
        return patients == null ? List.of() : patients.stream().map(PatientMapper::toDTO).toList();
    }

    static Patient fromDTO(PatientDTO patientDTO) {
        return new Patient(
                patientDTO.id(),
                patientDTO.name(),
                patientDTO.birthDate(),
                patientDTO.email(),
                patientDTO.password(),
                patientDTO.phone()
        );
    }

    @Override
    public Patient mapRow(ResultSet rs, int rowNum) {
        return new Patient(
                Utility.getIntOrNull(rs, "id"),
                Utility.getStringOrNull(rs, "name"),
                Utility.getLocalDateOrNull(rs, "birth_date"),
                Utility.getStringOrNull(rs, "email"),
                Utility.getStringOrNull(rs, "password"),
                Utility.getStringOrNull(rs, "phone")
        );
    }


}
