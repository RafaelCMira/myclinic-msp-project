package com.myclinic.appointment.online;

import com.myclinic.appointment.AppointmentDTO;
import com.myclinic.appointment.AppointmentFilterDTO;
import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/online-appointment")
class OnlineAppointmentController {

    private final OnlineAppointmentService onlineAppointmentService;

    OnlineAppointmentController(OnlineAppointmentService onlineAppointmentService) {
        this.onlineAppointmentService = onlineAppointmentService;
    }

    //region Insert
    @PostMapping()
    ResponseEntity<ApiResponse<AppointmentDTO>> insertAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        var appointment = onlineAppointmentService.insertAppointment(appointmentDTO);

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
        onlineAppointmentService.deleteAppointment(appointmentDTO);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result("Appointment deleted")
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion

    //region Get
    @GetMapping()
    ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointments(AppointmentFilterDTO filter) {
        var appointment = onlineAppointmentService.getAppointments(filter);

        ApiResponse<List<AppointmentDTO>> response = ApiResponse.<List<AppointmentDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(appointment)
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion
}
