package com.myclinic.exam;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Exam {
    private Integer patientId;
    private Integer clinicId;
    private String equipmentId;
    private LocalDate date;
    private LocalTime hour;
    private String motive;
}
