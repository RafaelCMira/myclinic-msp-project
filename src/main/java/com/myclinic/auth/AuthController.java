package com.myclinic.auth;

import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<UserDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        var user = authService.login(loginDTO.email(), loginDTO.password());

        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(user)
                .build();

        return ResponseEntity.ok().body(response);
    }

}
