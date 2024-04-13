package com.myclinic.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDTO(
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
        String phone,

        @NotBlank(message = "role is mandatory")
        String role
) {

    public static UserDTO withId(UserDTO user, Integer newId) {
        return new UserDTO(
                newId,
                user.name(),
                user.birthDate(),
                user.email(),
                user.password(),
                user.phone(),
                user.role()
        );
    }

}
