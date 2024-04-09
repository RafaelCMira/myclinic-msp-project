package com.myclinic.exam;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
class ExamMapper implements RowMapper<Exam> {

    static ExamDTO toDTO(Exam exam) {
        return new ExamDTO(
                exam.getPatientId(),
                exam.getClinicId(),
                exam.getEquipmentId(),
                exam.getDate(),
                exam.getHour(),
                exam.getMotive()
        );
    }

    static List<ExamDTO> toDTO(List<Exam> exams) {
        return exams == null ? List.of() : exams.stream().map(ExamMapper::toDTO).toList();
    }

    static Exam fromDTO(ExamDTO examDTO) {
        return Exam.builder()
                .patientId(examDTO.patientId())
                .clinicId(examDTO.clinicId())
                .equipmentId(examDTO.equipmentId())
                .date(examDTO.date())
                .hour(examDTO.hour())
                .motive(examDTO.motive())
                .build();
    }


    @Override
    public Exam mapRow(ResultSet rs, int rowNum) {
        return Exam.builder()
                .patientId(Utility.getIntOrNull(rs, "patient_id"))
                .clinicId(Utility.getIntOrNull(rs, "clinic_id"))
                .equipmentId(Utility.getStringOrNull(rs, "equipment_id"))
                .date(Utility.getLocalDateOrNull(rs, "date"))
                .hour(Utility.getLocalTimeOrNull(rs, "hour"))
                .motive(Utility.getStringOrNull(rs, "motive"))
                .build();
    }
}
