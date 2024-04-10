package com.myclinic.exam;

import com.myclinic.utils.Validations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class ExamRepository {

    private final JdbcTemplate db;
    private final ExamMapper examMapper;

    ExamRepository(JdbcTemplate db, ExamMapper examMapper) {
        this.db = db;
        this.examMapper = examMapper;
    }

    //region Insert
    void insertExam(Exam exam) {
        String query = """
                INSERT INTO exams (patient_id, clinic_id, equipment_id, date, hour, description, result)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        db.update(
                query,
                exam.getPatientId(),
                exam.getClinicId(),
                exam.getEquipmentId(),
                exam.getDate(),
                exam.getHour(),
                exam.getDescription(),
                exam.getResult()
        );
    }
    //endregion

    //region Delete
    int deleteExam(Exam exam) {
        String query = """
                DELETE FROM exams
                WHERE patient_id = ?
                    AND date = ?
                    AND hour = ?
                """;

        return db.update(
                query,
                exam.getPatientId(),
                exam.getDate(),
                exam.getHour()
        );
    }
    //endregion

    //region Get
    List<Exam> getByFilter(
            Optional<Integer> patientId,
            Optional<Integer> clinicId,
            Optional<Integer> equipmentId,
            Optional<String> date,
            Optional<String> hour,
            Optional<String> description,
            Optional<String> result) {

        StringBuilder query = new StringBuilder("""
                SELECT
                    patient_id,
                    clinic_id,
                    equipment_id,
                    date,
                    hour,
                    description,
                    result
                FROM
                    exams
                WHERE
                    1 = 1
                """
        );

        patientId.ifPresent(
                id -> query.append(" AND patient_id = ").append(id)
        );

        clinicId.ifPresent(
                id -> query.append(" AND clinic_id = ").append(id)
        );

        equipmentId.ifPresent(
                id -> query.append(" AND equipment_id = ").append(id)
        );

        date.ifPresent(
                s -> {
                    Validations.validate(s);
                    query.append(" AND date = '").append(s).append("'");
                }
        );

        hour.ifPresent(
                s -> {
                    Validations.validate(s);
                    query.append(" AND hour = '").append(s).append("'");
                }
        );

        description.ifPresent(
                s -> {
                    Validations.validate(s);
                    query.append(" AND description LIKE '").append(s).append("%'");
                }
        );

        result.ifPresent(
                s -> {
                    Validations.validate(s);
                    query.append(" AND result LIKE '").append(s).append("%'");
                }
        );

        return db.query(query.toString(), examMapper);
    }
    //endregion

}
