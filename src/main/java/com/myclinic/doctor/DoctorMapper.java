package com.myclinic.doctor;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DoctorMapper implements RowMapper<Doctor> {

    static DoctorDTO toDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getBirthDate()
        );
    }

    static List<DoctorDTO> toDTO(List<Doctor> doctors) {
        return doctors == null ? List.of() : doctors.stream().map(DoctorMapper::toDTO).toList();
    }

    static Doctor fromDTO(DoctorDTO doctorDTO) {
        return new Doctor(
                doctorDTO.id(),
                doctorDTO.name(),
                doctorDTO.email(),
                doctorDTO.phone(),
                doctorDTO.birthDate()
        );
    }

    @Override
    public Doctor mapRow(ResultSet rs, int rowNum) {
        return new Doctor(
                Utility.getIntOrNull(rs, "id"),
                Utility.getStringOrNull(rs, "name"),
                Utility.getStringOrNull(rs, "email"),
                Utility.getStringOrNull(rs, "phone"),
                Utility.getLocalDateOrNull(rs, "birth_date")
        );
    }
}
