package com.myclinic.doctor;

import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.utils.Validations;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
class DoctorService {

    private final DoctorRepository doctorRepository;

    DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    DoctorDTO insertDoctor(DoctorDTO doctorDTO) {
        Validations.validate(doctorDTO);

        var doctor = DoctorMapper.fromDTO(doctorDTO);
        var doctorId = doctorRepository.insertDoctor(doctor);

        if (doctorId.isEmpty())
            throw new NotFoundException(DoctorErrorMessages.INSERTING_DOCTOR_DB_ERROR.formatMsg(doctorDTO));

        return DoctorDTO.withId(doctorDTO, doctorId.get());
    }

    DoctorDTO getDoctor(Integer doctorId) {
        var doctorInfo = doctorRepository.findById(doctorId);

        var doctor = doctorInfo.orElseThrow(
                () -> new NotFoundException(DoctorErrorMessages.DOCTOR_NOT_FOUND.formatMsg(doctorId))
        );

        return DoctorMapper.toDTO(doctor);
    }

    List<DoctorDTO> getAll() {
        var doctors = doctorRepository.getAll();
        return DoctorMapper.toDTO(doctors);
    }

}
