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
    private String email;
    private String phone;
    private LocalDate birthDate;

    // add list of patients
}
