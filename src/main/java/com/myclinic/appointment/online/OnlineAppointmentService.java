package com.myclinic.appointment.online;

import com.myclinic.appointment.AppointmentDTO;
import com.myclinic.appointment.AppointmentErrorMessages;
import com.myclinic.appointment.AppointmentFilterDTO;
import com.myclinic.appointment.AppointmentMapper;
import com.myclinic.exception.customexceptions.AlreadyExistsException;
import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.utils.Validations;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OnlineAppointmentService {

    private final OnlineAppointmentRepository onlineAppointmentRepository;

    OnlineAppointmentService(OnlineAppointmentRepository onlineAppointmentRepository) {
        this.onlineAppointmentRepository = onlineAppointmentRepository;
    }

    //region Insert
    AppointmentDTO insertAppointment(AppointmentDTO appointmentDTO) {
        Validations.validate(appointmentDTO);

        var appointment = AppointmentMapper.fromDTO(appointmentDTO);
        try {
            onlineAppointmentRepository.insertAppointment(appointment);
        } catch (DataAccessException e) {
            //TODO -> Handle patient and doctor not exist (foreign key errors)

            throw new AlreadyExistsException(AppointmentErrorMessages.APPOINTMENT_ALREADY_EXISTS.formatMsg(appointmentDTO));
        }
        return AppointmentMapper.toDTO(appointment);
    }
    //endregion

    //region delete
    void deleteAppointment(AppointmentDTO appointmentDTO) {
        Validations.validate(appointmentDTO);

        var appointment = AppointmentMapper.fromDTO(appointmentDTO);
        var res = onlineAppointmentRepository.deleteAppointment(appointment);

        if (res == 0)
            throw new NotFoundException(AppointmentErrorMessages.APPOINTMENT_NOT_FOUND.formatMsg(appointmentDTO));
    }
    //endregion

    //region Get
    List<AppointmentDTO> getAppointments(AppointmentFilterDTO filter) {
        Validations.validate(filter);

        var onlineAppointments = onlineAppointmentRepository.findByFilter(
                filter.patientId(),
                filter.doctorId(),
                filter.date(),
                filter.hour(),
                filter.duration()
        );

        return AppointmentMapper.toDTO(onlineAppointments);
    }
    //endregion


}
