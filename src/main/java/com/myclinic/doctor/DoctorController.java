package com.myclinic.doctor;

import com.myclinic.utils.Validations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/doctor")
class DoctorController {

    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
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
