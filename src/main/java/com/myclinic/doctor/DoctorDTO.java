package com.myclinic.doctor;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record DoctorDTO(
        Integer id,

        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Birth date is mandatory")
        LocalDate birthDate,

        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotBlank(message = "Phone is mandatory")
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
