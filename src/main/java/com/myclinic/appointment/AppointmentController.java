package com.myclinic.appointment;

import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/appointment")
class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //region Insert
    @PostMapping()
    ResponseEntity<ApiResponse<AppointmentDTO>> insertAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        var appointment = appointmentService.insertAppointment(appointmentDTO);

        ApiResponse<AppointmentDTO> response = ApiResponse.<AppointmentDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(appointment)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion

    //region delete
    @DeleteMapping()
    ResponseEntity<ApiResponse<String>> deleteAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        appointmentService.deleteAppointment(appointmentDTO);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result("Appointment deleted")
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion

    //region Get
    @GetMapping()
    ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointments(
            @RequestParam(value = "patientId") Optional<Integer> patientId,
            @RequestParam(value = "doctorId") Optional<Integer> doctorId,
            @RequestParam(value = "clinicId") Optional<Integer> clinicId,
            @RequestParam(value = "date") Optional<String> date,
            @RequestParam(value = "hour") Optional<String> hour
    ) {
        var appointment = appointmentService.getAppointments(patientId, doctorId, clinicId, date, hour);

        ApiResponse<List<AppointmentDTO>> response = ApiResponse.<List<AppointmentDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(appointment)
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion
}
