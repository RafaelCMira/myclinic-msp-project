package com.myclinic.appointment;

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

    //region Insert
    void insertAppointment(Appointment appointment) {
        String query = """
                INSERT INTO
                    presential_appointments (patient_id, doctor_id, date, hour, duration, clinic_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        db.update(
                query,
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getDate(),
                appointment.getHour(),
                appointment.getDuration(),
                appointment.getClinicId()
        );
    }

    int reviewAppointment(Appointment appointment) {
        String query = """
                UPDATE
                    presential_appointments
                SET
                    rating = ?,
                    review = ?
                WHERE
                    patient_id = ?
                    AND doctor_id = ?
                    AND date = ?
                    AND hour = ?
                """;

        return db.update(
                query,
                appointment.getRating(),
                appointment.getReview(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getDate(),
                appointment.getHour()
        );
    }
    //endregion

    //region Delete
    int deleteAppointment(Appointment appointment) {
        String query = """
                DELETE FROM
                    presential_appointments
                WHERE
                    patient_id = ?
                    AND doctor_id = ?
                    AND date = ?
                    AND hour = ?
                """;

        return db.update(
                query,
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getDate(),
                appointment.getHour()
        );
    }
    //endregion

    //region Get
    List<Appointment> findByFilter(
            Optional<Integer> patientId,
            Optional<Integer> doctorId,
            Optional<Integer> clinicId,
            Optional<String> date,
            Optional<String> hour,
            Optional<String> duration) {

        StringBuilder query = new StringBuilder("""
                SELECT
                    patient_id,
                    doctor_id,
                    clinic_id,
                    date,
                    hour,
                    duration,
                    rating,
                    review
                FROM
                    presential_appointments
                WHERE
                    1=1
                """
        );

        patientId.ifPresent(
                id -> query.append(" AND patient_id = ").append(id)
        );

        doctorId.ifPresent(
                id -> query.append(" AND doctor_id = ").append(id)
        );

        clinicId.ifPresent(
                id -> query.append(" AND clinic_id = ").append(id)
        );

        date.ifPresent(
                s -> query.append(String.format(" AND date = '%s'", s))
        );

        hour.ifPresent(
                s -> query.append(String.format(" AND hour = '%s'", s))
        );

        duration.ifPresent(
                s -> query.append(String.format(" AND duration = '%s'", s))
        );

        return db.query(query.toString(), appointmentMapper);
    }
    //endregion

}
