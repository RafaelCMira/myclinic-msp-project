package com.myclinic.doctor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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

    // Writes

    Optional<Integer> insertDoctor(Doctor doctor) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = """
                INSERT INTO doctor (name, birth_date, email, password, phone)
                VALUES (?, ?, ?, ?, ?)
                """;

        db.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, doctor.getName());
            ps.setDate(2, java.sql.Date.valueOf(doctor.getBirthDate()));
            ps.setString(3, doctor.getEmail());
            ps.setString(4, doctor.getPassword());
            ps.setString(5, doctor.getPhone());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::intValue);
    }

    void updateDoctor(Doctor doctor) {
        String query = """
                UPDATE doctor
                SET
                    name = ?,
                    birth_date = ?,
                    email = ?,
                    phone = ?
                WHERE
                    doctor_id = ?
                """;

        db.update(query,
                doctor.getName(),
                java.sql.Date.valueOf(doctor.getBirthDate()),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getId()
        );

    }

    void deleteDoctor(Integer doctorId) {
        String query = """
                DELETE FROM doctor
                WHERE doctor_id = ?
                """;

        db.update(query, doctorId);
    }

    // Reads
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
