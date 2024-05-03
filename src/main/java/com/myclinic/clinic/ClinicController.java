package com.myclinic.clinic;

import com.myclinic.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/clinic")
@Slf4j
class ClinicController {

    private final ClinicService clinicService;

    ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping()
    ResponseEntity<ApiResponse<List<ClinicDTO>>> getClinicsByFilter(ClinicFilterDTO filter) {
        log.info("Getting clinics by filter: {}", filter);

        var clinics = clinicService.getClinicsByFilter(filter);

        ApiResponse<List<ClinicDTO>> response = ApiResponse.<List<ClinicDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(clinics)
                .build();

        return ResponseEntity.ok().body(response);
    }
}
