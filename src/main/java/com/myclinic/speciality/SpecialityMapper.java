package com.myclinic.speciality;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class SpecialityMapper implements RowMapper<Speciality> {

    static SpecialityDTO toDTO(Speciality speciality) {
        return new SpecialityDTO(speciality.getId(), speciality.getName());
    }

    static List<SpecialityDTO> toDTO(List<Speciality> specialities) {
        return specialities == null ? List.of() : specialities.stream().map(SpecialityMapper::toDTO).toList();
    }

    static Speciality fromDTO(SpecialityDTO specialityDTO) {
        return new Speciality(specialityDTO.id(), specialityDTO.name());
    }

    @Override
    public Speciality mapRow(ResultSet rs, int rowNum) {
        return new Speciality(
                Utility.getIntOrNull(rs, "id"),
                Utility.getStringOrNull(rs, "name")
        );
    }

}
