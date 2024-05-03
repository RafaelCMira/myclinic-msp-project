package com.myclinic.doctor;

import java.util.Optional;

public record DoctorFilterDTO(
        Optional<Integer> speciality,
        Optional<Integer> clinic
) {
}
