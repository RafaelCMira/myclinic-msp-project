package com.myclinic.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phone;

    // add list of specialties

    // add list of patients

    // add list of appointments

    // add list of clinics
}
