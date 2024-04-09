package com.myclinic.exam;

import com.myclinic.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/exam")
class ExamController {

    private final ExamService examService;

    ExamController(ExamService examService) {
        this.examService = examService;
    }

    //region Insert
    @PostMapping()
    ResponseEntity<ApiResponse<ExamDTO>> insertExam(@Valid @RequestBody ExamDTO examDTO) {
        var exam = examService.insertExam(examDTO);

        ApiResponse<ExamDTO> response = ApiResponse.<ExamDTO>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(exam)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion

    //region Delete
    @DeleteMapping()
    ResponseEntity<ApiResponse<String>> deleteAppointment(@Valid @RequestBody ExamDTO examDTO) {
        examService.deleteExam(examDTO);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result("Exam deleted")
                .build();

        return ResponseEntity.ok().body(response);
    }
    //endregion

    //region Get
    @GetMapping()
    ResponseEntity<ApiResponse<List<ExamDTO>>> getExams(
            @RequestParam Optional<Integer> patientId,
            @RequestParam Optional<Integer> clinicId,
            @RequestParam Optional<String> date,
            @RequestParam Optional<String> hour,
            @RequestParam Optional<String> motive) {

        var exams = examService.getExams(patientId, clinicId, date, hour, motive);

        ApiResponse<List<ExamDTO>> response = ApiResponse.<List<ExamDTO>>builder()
                .status(ApiResponse.Status.SUCCESS.name())
                .result(exams)
                .build();

        return ResponseEntity.ok(response);
    }
    //endregion

}
