package com.myclinic.doctor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class DoctorRepository {

    private final JdbcTemplate db;
    private final DoctorMapper doctorMapper;

    DoctorRepository(JdbcTemplate db, DoctorMapper doctorMapper) {
        this.db = db;
        this.doctorMapper = doctorMapper;
    }

    Optional<Doctor> findById(Integer doctorId) {
        String query = """
                SELECT
                    doctor_id AS id,
                    name,
                    email,
                    phone,
                    birth_date
                FROM
                    doctor
                WHERE
                    doctor_id = ?
                """;

        return db.query(query, doctorMapper, doctorId).stream().findFirst();
    }

    List<Doctor> getAll() {
        String query = """
                SELECT
                    doctor_id AS id,
                    name,
                    email,
                    phone,
                    birth_date
                FROM doctor
                """;

        return db.query(query, doctorMapper);
    }
}
