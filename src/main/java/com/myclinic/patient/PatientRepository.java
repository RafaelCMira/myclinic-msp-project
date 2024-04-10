package com.myclinic.patient;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
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
    Integer insertPatient(Patient patient) {
        String procedureCall = "{call insert_patient(?, ?, ?, ?, ?, ?)}";

        var res = db.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
            cs.setString(1, patient.getName());
            cs.setDate(2, java.sql.Date.valueOf(patient.getBirthDate()));
            cs.setString(3, patient.getEmail());
            cs.setString(4, patient.getPassword());
            cs.setString(5, patient.getPhone());
            cs.registerOutParameter(6, Types.INTEGER);
            cs.execute();
            return cs.getInt(6);
        });

        return Optional.ofNullable(res).orElse(0);
    }
    //endregion

    //region Update
    int updatePatient(Patient patient) {
        String query = """
                UPDATE users
                SET
                    name = ?,
                    birth_date = ?,
                    email = ?,
                    password = ?,
                    phone = ?
                WHERE
                    user_id = ?
                """;

        return db.update(
                query,
                patient.getName(),
                java.sql.Date.valueOf(patient.getBirthDate()),
                patient.getEmail(),
                patient.getPassword(),
                patient.getPhone(),
                patient.getId()
        );
    }
    //endregion

    //region Delete
    int deletePatient(Integer patientId) {
        String procedureCall = "{call delete_patient(?)}";

        var res = db.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
            cs.setInt(1, patientId);
            cs.executeUpdate();
            return cs.getUpdateCount();
        });

        return Optional.ofNullable(res).orElse(0);
    }
    //endregion

    //region Get
    Optional<Patient> findById(Integer patientId) {
        String query = """
                SELECT
                    user_id AS id,
                    u.name,
                    u.email,
                    u.phone,
                    u.birth_date
                FROM
                    users u INNER JOIN patients p
                    ON u.user_id = p.patient_id
                WHERE
                    user_id = ?
                """;

        return db.query(query, patientMapper, patientId)
                .stream()
                .findFirst();
    }

    List<Patient> getALL() {
        String query = """
                SELECT
                    user_id AS id,
                    u.name,
                    u.email,
                    u.phone,
                    u.birth_date
                FROM
                    users u INNER JOIN patients p
                    ON u.user_id = p.patient_id
                """;

        return db.query(query, patientMapper);
    }
    //endregion
}
