package com.myclinic.appointment;

import com.myclinic.exception.customexceptions.AlreadyExistsException;
import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.utils.Validations;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    //region Insert
    AppointmentDTO insertAppointment(AppointmentDTO appointmentDTO) {
        Validations.validate(appointmentDTO);

        var appointment = AppointmentMapper.fromDTO(appointmentDTO);
        try {
            appointmentRepository.insertAppointment(appointment);
        } catch (DataAccessException e) {
            throw new AlreadyExistsException(AppointmentErrorMessages.APPOINTMENT_ALREADY_EXISTS.formatMsg(appointmentDTO));
        }
        return AppointmentMapper.toDTO(appointment);
    }
    //endregion

    //region delete
    void deleteAppointment(AppointmentDTO appointmentDTO) {
        Validations.validate(appointmentDTO);

        var appointment = AppointmentMapper.fromDTO(appointmentDTO);
        var res = appointmentRepository.deleteAppointment(appointment);

        if (res == 0)
            throw new NotFoundException(AppointmentErrorMessages.APPOINTMENT_NOT_FOUND.formatMsg(appointmentDTO));
    }
    //endregion

    //region Get
    List<AppointmentDTO> getAppointments(
            Optional<Integer> patientId,
            Optional<Integer> doctorId,
            Optional<Integer> clinicId,
            Optional<String> date,
            Optional<String> hour,
            Optional<String> duration) {

        var appointments = appointmentRepository.findByFilter(patientId, doctorId, clinicId, date, hour, duration);

        return AppointmentMapper.toDTO(appointments);
    }
    //endregion
}
