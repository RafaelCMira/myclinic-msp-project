package com.myclinic.doctor;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Doctor {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phone;

    private Double rating;

    // add list of specialties

    // add list of patients

    // add list of appointments

    // add list of clinics
}
