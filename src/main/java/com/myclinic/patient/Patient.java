package com.myclinic.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phone;

    // add list of appointments

    // add list of exams

    // add list of prescriptions

}
