package com.myclinic.appointment;

import com.myclinic.utils.Utility;
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
            Optional<String> duration,
            Optional<Boolean> upcomingAppointments) {

        StringBuilder query = new StringBuilder("""
                SELECT
                    patient_id,
                    doctor_id,
                    users.name as doctor_name,
                    clinic_id,
                    clinics.name as clinic_name,
                    date,
                    hour,
                    duration,
                    rating,
                    review
                FROM
                    presential_appointments
                    INNER JOIN users ON (doctor_id = user_id)
                    INNER JOIN clinics USING (clinic_id)
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

        upcomingAppointments.ifPresent(
                upcoming -> {
                    String currentDate = Utility.getCurrentDate();

                    if (upcoming) {
                        query.append(String.format(" AND date >= '%s'", currentDate));
                    } else {
                        query.append(String.format(" AND date <= '%s'", currentDate));
                    }
                }
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
