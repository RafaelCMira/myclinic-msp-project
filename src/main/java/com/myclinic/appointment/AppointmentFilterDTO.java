package com.myclinic.appointment;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppointmentFilterDTO(
        Optional<Integer> patientId,
        Optional<Integer> doctorId,
        Optional<Integer> clinicId,
        Optional<String> date,
        Optional<String> hour,
        Optional<String> duration,
        Optional<Boolean> upcoming
) {
}
