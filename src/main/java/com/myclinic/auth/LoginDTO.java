package com.myclinic.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        
        @NotBlank(message = "email is mandatory")
        String email,

        @NotBlank(message = "password is mandatory")
        String password
) {

}
