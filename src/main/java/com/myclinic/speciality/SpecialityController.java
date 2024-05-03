package com.myclinic.speciality;

import com.myclinic.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/speciality")
class SpecialityController {

    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping()
    ResponseEntity<ApiResponse<List<SpecialityDTO>>> getSpecialities() {
        var specialities = specialityService.getSpecialities();

        ApiResponse<List<SpecialityDTO>> response = ApiResponse.<List<SpecialityDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(specialities)
                .build();

        return ResponseEntity.ok().body(response);
    }

}
