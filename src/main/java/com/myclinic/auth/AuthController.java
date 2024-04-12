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

    @PostMapping("/register")
    ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody UserDTO userDTO) {
        System.out.println(userDTO);

        var res = authService.register(userDTO);

        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(res)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        authService.login(loginDTO.email(), loginDTO.password());

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result("Success login!")
                .build();

        return ResponseEntity.ok().body(response);
    }


}
