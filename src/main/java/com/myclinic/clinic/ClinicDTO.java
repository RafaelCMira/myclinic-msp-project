package com.myclinic.clinic;

public record ClinicDTO(
        Integer id,
        String name,
        String phone,
        String location
) {
}
