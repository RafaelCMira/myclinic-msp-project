package com.myclinic.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
class PatientRepository {

    private final JdbcTemplate db;
    private final PatientMapper patientMapper;

    PatientRepository(JdbcTemplate db, PatientMapper patientMapper) {
        this.db = db;
        this.patientMapper = patientMapper;
    }

    //region Insert
    Optional<Integer> insertPatient(Patient patient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = """
                INSERT INTO patient (name, birth_date, email, password, phone)
                VALUES (?, ?, ?, ?, ?)
                """;

        db.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, patient.getName());
            ps.setDate(2, java.sql.Date.valueOf(patient.getBirthDate()));
            ps.setString(3, patient.getEmail());
            ps.setString(4, patient.getPassword());
            ps.setString(5, patient.getPhone());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::intValue);
    }
    //endregion

    //region Update
    int updatePatient(Patient patient) {
        String query = """
                UPDATE patient
                SET
                    name = ?,
                    birth_date = ?,
                    email = ?,
                    phone = ?
                WHERE
                    patient_id = ?
                """;

        return db.update(
                query,
                patient.getName(),
                java.sql.Date.valueOf(patient.getBirthDate()),
                patient.getEmail(),
                patient.getPhone(),
                patient.getId()
        );
    }
    //endregion

    //region Delete
    int deletePatient(Integer patientId) {
        String query = """
                DELETE FROM patient
                WHERE patient_id = ?
                """;

        return db.update(query, patientId);
    }
    //endregion

    //region Get
    Optional<Patient> findById(Integer patientId) {
        String query = """
                SELECT
                    patient_id AS id,
                    name,
                    email,
                    phone,
                    birth_date
                FROM
                    patient
                WHERE
                    patient_id = ?
                """;

        return db.query(query, patientMapper, patientId)
                .stream()
                .findFirst();
    }

    List<Patient> getALL() {
        String query = """
                SELECT
                    patient_id AS id,
                    name,
                    email,
                    phone,
                    birth_date
                FROM
                    patient
                """;

        return db.query(query, patientMapper);
    }
    //endregion
}
