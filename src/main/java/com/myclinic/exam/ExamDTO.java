package com.myclinic.exam;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExamDTO(
        Integer patientId,
        Integer clinicId,
        String equipmentId,
        LocalDate date,
        LocalTime hour,
        String description,
        String result
) {
}
