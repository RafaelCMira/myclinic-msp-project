package com.myclinic.doctor;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DoctorDTO(

        Integer id,

        @NotBlank(message = "name is mandatory")
        String name,

        @NotNull(message = "Birthdate is mandatory")
        LocalDate birthDate,

        @NotBlank(message = "email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotBlank(message = "phone is mandatory")
        String phone

        // add list of specialties

        // add list of patients

        // add list of appointments

        // add list of clinics
) {
    public static DoctorDTO withId(DoctorDTO doctor, Integer newId) {
        return new DoctorDTO(
                newId,
                doctor.name(),
                doctor.birthDate(),
                doctor.email(),
                doctor.password(),
                doctor.phone());
    }

}
