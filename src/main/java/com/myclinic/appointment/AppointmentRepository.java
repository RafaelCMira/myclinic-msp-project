package com.myclinic.appointment;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class AppointmentRepository {

    private final JdbcTemplate db;
    private final AppointmentMapper appointmentMapper;

    AppointmentRepository(JdbcTemplate db, AppointmentMapper appointmentMapper) {
        this.db = db;
        this.appointmentMapper = appointmentMapper;
    }

    private int executeAppointmentProcedure(Appointment appointment, String procedureCall) {
        var res = db.execute(procedureCall, (CallableStatementCallback<Integer>) cs -> {
            cs.setInt(1, appointment.getPatientId());
            cs.setInt(2, appointment.getDoctorId());
            cs.setInt(3, appointment.getClinicId());
            cs.setDate(4, java.sql.Date.valueOf(appointment.getDate()));
            cs.setTime(5, java.sql.Time.valueOf(appointment.getHour()));
            return cs.executeUpdate();
        });

        return Optional.ofNullable(res).orElse(0);
    }

    //region Insert
    void insertAppointment(Appointment appointment) {
        String procedureCall = "{call insert_appointment(?, ?, ?, ?, ?)}";
        executeAppointmentProcedure(appointment, procedureCall);
    }
    //endregion

    //region Delete
    int deleteAppointment(Appointment appointment) {
        String procedureCall = "{call delete_appointment(?, ?, ?, ?, ?)}";
        return executeAppointmentProcedure(appointment, procedureCall);
    }
    //endregion

    //region Get
    List<Appointment> findByFilter(
            Optional<Integer> patientId,
            Optional<Integer> doctorId,
            Optional<Integer> clinicId,
            Optional<String> date,
            Optional<String> time) {

        StringBuilder query = new StringBuilder("""
                SELECT
                    patient_id,
                    doctor_id,
                    clinic_id,
                    date,
                    hour
                FROM
                    presential
                WHERE
                    1=1
                """
        );

        patientId.ifPresent(
                integer -> query.append(String.format(" AND patient_id = %d", integer))
        );

        doctorId.ifPresent(
                integer -> query.append(String.format(" AND doctor_id = %d", integer))
        );

        clinicId.ifPresent(
                integer -> query.append(String.format(" AND clinic_id = %d", integer))
        );

        date.ifPresent(
                s -> query.append(String.format(" AND date = '%s'", s))
        );

        time.ifPresent(
                s -> query.append(String.format(" AND hour = '%s'", s))
        );

        return db.query(query.toString(), appointmentMapper);
    }
    //endregion

}
