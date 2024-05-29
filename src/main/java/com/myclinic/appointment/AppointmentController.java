package com.myclinic.appointment;

import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //region Update
    @PutMapping()
    ResponseEntity<ApiResponse<Void>> reviewAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        appointmentService.reviewAppointment(appointmentDTO);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    //endregion

    //region delete
    @DeleteMapping()
    ResponseEntity<ApiResponse<String>> deleteAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        appointmentService.deleteAppointment(appointmentDTO);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    //endregion

    //region Get
    @GetMapping()
    ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointments(AppointmentFilterDTO filter) {
        var appointments = appointmentService.getAppointments(filter);

        ApiResponse<List<AppointmentDTO>> response = ApiResponse.<List<AppointmentDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(appointments)
                .build();

        return ResponseEntity.ok().body(response);
    }

    // TODO - LIST APPOINTMENTS before current date
    //endregion
}
