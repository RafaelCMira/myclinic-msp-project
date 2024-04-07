package com.myclinic.patient;

import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //region Insert
    @PostMapping()
    ResponseEntity<ApiResponse<PatientDTO>> insertPatient(@Valid @RequestBody PatientDTO patientDTO) {
        var patient = patientService.insertPatient(patientDTO);

        ApiResponse<PatientDTO> response = ApiResponse.<PatientDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(patient)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion

    //region Update
    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<PatientDTO>> updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientDTO patientDTO) {
        var patient = patientService.updatePatient(id, patientDTO);

        ApiResponse<PatientDTO> response = ApiResponse.<PatientDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(patient)
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion

    //region Delete
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result("Patient deleted successfully " + id)
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion

    //region Get
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<PatientDTO>> getPatient(@PathVariable Integer id) {
        var patient = patientService.getPatient(id);

        ApiResponse<PatientDTO> response = ApiResponse.<PatientDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(patient)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    ResponseEntity<ApiResponse<List<PatientDTO>>> getAllPatients() {
        var patients = patientService.getAll();

        ApiResponse<List<PatientDTO>> response = ApiResponse.<List<PatientDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(patients)
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion
}
