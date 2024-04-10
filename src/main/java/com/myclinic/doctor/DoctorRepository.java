package com.myclinic.doctor;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
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

    //region Insert
    Integer insertDoctor(Doctor doctor) {
        String procedureCall = "{call insert_doctor(?, ?, ?, ?, ?, ?)}";

        var res = db.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
            cs.setString(1, doctor.getName());
            cs.setDate(2, java.sql.Date.valueOf(doctor.getBirthDate()));
            cs.setString(3, doctor.getEmail());
            cs.setString(4, doctor.getPassword());
            cs.setString(5, doctor.getPhone());
            cs.registerOutParameter(6, Types.INTEGER);
            cs.execute();
            return cs.getInt(6);
        });

        return Optional.ofNullable(res).orElse(0);
    }
    //endregion

    //region Update
    int updateDoctor(Doctor doctor) {
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

        return db.update(query,
                doctor.getName(),
                java.sql.Date.valueOf(doctor.getBirthDate()),
                doctor.getEmail(),
                doctor.getPassword(),
                doctor.getPhone(),
                doctor.getId()
        );
    }
    //endregion

    //region Delete
    int deleteDoctor(Integer doctorId) {
        String procedureCall = "{call delete_doctor(?)}";

        var res = db.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
            cs.setInt(1, doctorId);
            cs.executeUpdate();
            return cs.getUpdateCount();
        });

        return Optional.ofNullable(res).orElse(0);
    }
    //endregion

    //region Get
    Optional<Doctor> findById(Integer doctorId) {
        String query = """
                SELECT
                    doctor_id AS id,
                    u.name,
                    u.email,
                    u.phone,
                    u.birth_date
                FROM
                    users u INNER JOIN doctors d
                    ON u.user_id = d.doctor_id
                WHERE
                    doctor_id = ?
                """;

        return db.query(query, doctorMapper, doctorId)
                .stream()
                .findFirst();
    }

    List<Doctor> getAll() {
        String query = """
                SELECT
                    user_id AS id,
                    u.name,
                    u.email,
                    u.phone,
                    u.birth_date
                FROM
                    users u INNER JOIN doctors d
                    ON u.user_id = d.doctor_id
                """;

        return db.query(query, doctorMapper);
    }
    //endregion
}
