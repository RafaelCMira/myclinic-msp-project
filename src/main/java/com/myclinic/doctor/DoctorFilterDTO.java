package com.myclinic.doctor;

import java.util.Optional;

public record DoctorFilterDTO(
        Optional<String> speciality
) {
}
