package com.myclinic.clinic;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
class ClinicMapper implements RowMapper<Clinic> {

    static ClinicDTO toDTO(Clinic clinic) {
        return new ClinicDTO(
                clinic.getId(),
                clinic.getName(),
                clinic.getPhone(),
                clinic.getLocation()
        );
    }

    static List<ClinicDTO> toDTO(List<Clinic> clinics) {
        return clinics == null ? List.of() : clinics.stream().map(ClinicMapper::toDTO).toList();
    }

    static Clinic fromDTO(ClinicDTO clinicDTO) {
        return Clinic.builder()
                .id(clinicDTO.id())
                .name(clinicDTO.name())
                .phone(clinicDTO.phone())
                .location(clinicDTO.location())
                .build();
    }

    @Override
    public Clinic mapRow(ResultSet rs, int rowNum) {
        return Clinic.builder()
                .id(Utility.getIntOrNull(rs, "id"))
                .name(Utility.getStringOrNull(rs, "name"))
                .phone(Utility.getStringOrNull(rs, "phone"))
                .location(Utility.getStringOrNull(rs, "location"))
                .build();
    }
}
