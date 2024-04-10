package com.myclinic.appointment;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    private Integer patientId;
    private Integer doctorId;
    private Integer clinicId;
    private LocalDate date;
    private LocalTime hour;
    private LocalTime duration;
}
