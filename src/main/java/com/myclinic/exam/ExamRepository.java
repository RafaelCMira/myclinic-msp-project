package com.myclinic.exam;

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
                INSERT INTO exam (patient_id, clinic_id, date, hour, motive)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        db.update(
                query,
                exam.getPatientId(),
                exam.getClinicId(),
                exam.getDate(),
                exam.getHour(),
                exam.getMotive()
        );
    }
    //endregion
    
    //region Get
    List<Exam> getByFilter(
            Optional<Integer> patientId,
            Optional<Integer> clinicId,
            Optional<String> date,
            Optional<String> hour,
            Optional<String> motive) {

        StringBuilder query = new StringBuilder("""
                SELECT
                    patient_id,
                    clinic_id,
                    date,
                    hour,
                    motive
                FROM exam
                WHERE exam_id = ?
                """
        );

        patientId.ifPresent(
                id -> query.append(" AND patient_id = ").append(id)
        );

        clinicId.ifPresent(
                id -> query.append(" AND clinic_id = ").append(id)
        );

        date.ifPresent(
                s -> query.append(" AND date = '").append(s).append("'")
        );

        hour.ifPresent(
                s -> query.append(" AND hour = '").append(s).append("'")
        );

        motive.ifPresent(
                s -> query.append(" AND motive LIKE = '").append(s).append("%'")
        );

        return db.query(query.toString(), examMapper);
    }
    //endregion

}
