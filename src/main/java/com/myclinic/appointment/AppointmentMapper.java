package com.myclinic.appointment;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class AppointmentMapper implements RowMapper<Appointment> {

    public static AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getDoctorName(),
                appointment.getClinicId(),
                appointment.getDate(),
                appointment.getHour(),
                appointment.getDuration(),
                appointment.getRating(),
                appointment.getReview()
        );
    }

    public static List<AppointmentDTO> toDTO(List<Appointment> appointments) {
        return appointments == null ? List.of() : appointments.stream().map(AppointmentMapper::toDTO).toList();
    }

    public static Appointment fromDTO(AppointmentDTO appointmentDTO) {
        return new Appointment(
                appointmentDTO.patientId(),
                appointmentDTO.doctorId(),
                appointmentDTO.doctorName(),
                appointmentDTO.clinicId(),
                appointmentDTO.date(),
                appointmentDTO.hour(),
                appointmentDTO.duration(),
                appointmentDTO.rating(),
                appointmentDTO.review()
        );
    }

    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) {
        return new Appointment(
                Utility.getIntOrNull(rs, "patient_id"),
                Utility.getIntOrNull(rs, "doctor_id"),
                Utility.getStringOrNull(rs, "doctor_name"),
                Utility.getIntOrNull(rs, "clinic_id"),
                Utility.getLocalDateOrNull(rs, "date"),
                Utility.getLocalTimeOrNull(rs, "hour"),
                Utility.getLocalTimeOrNull(rs, "duration"),
                Utility.getIntOrNull(rs, "rating"),
                Utility.getStringOrNull(rs, "review")
        );
    }
}
