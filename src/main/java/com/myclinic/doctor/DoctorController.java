package com.myclinic.doctor;

import com.myclinic.utils.Validations;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    ResponseEntity<DoctorDTO> insertDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        Validations.validate(doctorDTO);
        var doctor = doctorService.insertDoctor(doctorDTO);
        return ResponseEntity.ok().body(doctor);
    }

    @GetMapping("/{id}")
    ResponseEntity<DoctorDTO> getDoctor(@PathVariable Integer id) {
        Validations.validate(id);
        var doctor = doctorService.getDoctor(id);
        return ResponseEntity.ok().body(doctor);
    }

    @GetMapping()
    ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        var doctors = doctorService.getAll();
        return ResponseEntity.ok().body(doctors);
    }


}
