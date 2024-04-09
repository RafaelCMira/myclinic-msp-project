package com.myclinic.exam;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
record ExamDTO(
        Integer patientId,
        Integer clinicId,
        String equipmentId,
        LocalDate date,
        LocalTime time,
        String motive
) {
}
