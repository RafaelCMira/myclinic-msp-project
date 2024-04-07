package com.myclinic.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PatientDTO(

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

        // add list of appointments

        // add list of exams

        // add list of prescriptions
) {
    public static PatientDTO withId(PatientDTO doctor, Integer newId) {
        return new PatientDTO(
                newId,
                doctor.name(),
                doctor.birthDate(),
                doctor.email(),
                doctor.password(),
                doctor.phone());
    }
}
