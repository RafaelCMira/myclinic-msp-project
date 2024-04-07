package com.myclinic.doctor;

import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/doctor")
class DoctorController {

    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping()
    ResponseEntity<ApiResponse<DoctorDTO>> insertDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        var doctor = doctorService.insertDoctor(doctorDTO);

        ApiResponse<DoctorDTO> response = ApiResponse.<DoctorDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(doctor)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<DoctorDTO>> updateDoctor(@PathVariable Integer id, @Valid @RequestBody DoctorDTO doctorDTO) {
        var doctor = doctorService.updateDoctor(id, doctorDTO);

        ApiResponse<DoctorDTO> response = ApiResponse.<DoctorDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(doctor)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<DoctorDTO>> getDoctor(@PathVariable Integer id) {
        var doctor = doctorService.getDoctor(id);

        ApiResponse<DoctorDTO> response = ApiResponse.<DoctorDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(doctor)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    ResponseEntity<ApiResponse<List<DoctorDTO>>> getAllDoctors() {
        var doctors = doctorService.getAll();

        ApiResponse<List<DoctorDTO>> response = ApiResponse.<List<DoctorDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(doctors)
                .build();

        return ResponseEntity.ok().body(response);
    }


}
