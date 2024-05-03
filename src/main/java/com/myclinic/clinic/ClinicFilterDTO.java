package com.myclinic.clinic;

import java.util.Optional;

public record ClinicFilterDTO(
        Optional<String> location,
        Optional<String> speciality
) {
}
