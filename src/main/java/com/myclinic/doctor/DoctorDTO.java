package com.myclinic.doctor;

import java.time.LocalDate;

public record DoctorDTO(
        Integer id,
        String name,
        String email,
        String phone,
        LocalDate birthDate

        // add list of patients
) {
}
