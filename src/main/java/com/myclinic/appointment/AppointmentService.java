package com.myclinic.appointment;

import com.myclinic.exception.customexceptions.AlreadyExistsException;
import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.utils.Validations;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            //TODO -> Handle patient and doctor not exist (foreign key errors)

            throw new AlreadyExistsException(AppointmentErrorMessages.APPOINTMENT_ALREADY_EXISTS.formatMsg(appointmentDTO));
        }
        return AppointmentMapper.toDTO(appointment);
    }
    //endregion

    //region Update
    void reviewAppointment(AppointmentDTO appointmentDTO) {
        Validations.validate(appointmentDTO);

        var appointment = AppointmentMapper.fromDTO(appointmentDTO);
        var res = appointmentRepository.reviewAppointment(appointment);

        if (res == 0)
            throw new NotFoundException(AppointmentErrorMessages.APPOINTMENT_NOT_FOUND.formatMsg(appointmentDTO));
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
    List<AppointmentDTO> getAppointments(AppointmentFilterDTO filter) {
        Validations.validate(filter);

        var appointments = appointmentRepository.findByFilter(
                filter.patientId(),
                filter.doctorId(),
                filter.clinicId(),
                filter.date(),
                filter.hour(),
                filter.duration(),
                filter.upcoming()
        );

        return AppointmentMapper.toDTO(appointments);
    }
    //endregion
}
