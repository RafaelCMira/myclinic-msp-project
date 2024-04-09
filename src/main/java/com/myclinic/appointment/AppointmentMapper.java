package com.myclinic.appointment;

import com.myclinic.utils.Utility;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
class AppointmentMapper implements RowMapper<Appointment> {

    static AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getClinicId(),
                appointment.getDate(),
                appointment.getHour()
        );
    }

    static List<AppointmentDTO> toDTO(List<Appointment> appointments) {
        return appointments == null ? List.of() : appointments.stream().map(AppointmentMapper::toDTO).toList();
    }

    static Appointment fromDTO(AppointmentDTO appointmentDTO) {
        return new Appointment(
                appointmentDTO.patientId(),
                appointmentDTO.doctorId(),
                appointmentDTO.clinicId(),
                appointmentDTO.date(),
                appointmentDTO.hour()
        );
    }

    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) {
        return new Appointment(
                Utility.getIntOrNull(rs, "patient_id"),
                Utility.getIntOrNull(rs, "doctor_id"),
                Utility.getIntOrNull(rs, "clinic_id"),
                Utility.getLocalDateOrNull(rs, "date"),
                Utility.getLocalTimeOrNull(rs, "hour")
        );
    }
}
