package com.myclinic.appointment;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppointmentDTO(
        Integer patientId,
        Integer doctorId,
        String doctorName,
        Integer clinicId,
        LocalDate date,
        LocalTime hour,
        LocalTime duration,
        Integer rating,
        String review
) {
}
