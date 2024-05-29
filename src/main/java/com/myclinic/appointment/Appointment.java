package com.myclinic.appointment;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Appointment {
    private Integer patientId;
    private Integer doctorId;
    private String doctorName;
    private Integer clinicId;
    private LocalDate date;
    private LocalTime hour;
    private LocalTime duration;
    private Integer rating;
    private String review;
}
