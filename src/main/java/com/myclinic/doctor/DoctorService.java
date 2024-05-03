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

    //region Insert
    DoctorDTO insertDoctor(DoctorDTO doctorDTO) {
        Validations.validate(doctorDTO);

        var doctor = DoctorMapper.fromDTO(doctorDTO);
        var doctorId = doctorRepository.insertDoctor(doctor);

        if (doctorId == 0)
            throw new NotFoundException(DoctorErrorMessages.INSERTING_DOCTOR_DB_ERROR.formatMsg(doctorDTO));

        return DoctorDTO.withId(doctorDTO, doctorId);
    }
    //endregion

    //region Update
    DoctorDTO updateDoctor(Integer doctorId, DoctorDTO doctorDTO) {
        Validations.validate(doctorDTO);

        var doctor = DoctorMapper.fromDTO(doctorDTO);
        doctor.setId(doctorId);

        var res = doctorRepository.updateDoctor(doctor);

        if (res == 0) {
            throw new NotFoundException(DoctorErrorMessages.DOCTOR_NOT_FOUND.formatMsg(doctorId));
        }

        return DoctorMapper.toDTO(doctor);
    }
    //endregion

    //region Delete
    void deleteDoctor(Integer doctorId) {
        var res = doctorRepository.deleteDoctor(doctorId);

        if (res == 0) {
            throw new NotFoundException(DoctorErrorMessages.DOCTOR_NOT_FOUND.formatMsg(doctorId));
        }
    }
    //endregion

    //region Get
    DoctorDTO getDoctor(Integer doctorId) {
        var doctorInfo = doctorRepository.findById(doctorId);

        var doctor = doctorInfo.orElseThrow(
                () -> new NotFoundException(DoctorErrorMessages.DOCTOR_NOT_FOUND.formatMsg(doctorId))
        );

        return DoctorMapper.toDTO(doctor);
    }

    List<DoctorDTO> getDoctorsByFilter(DoctorFilterDTO filter) {
        List<Doctor> doctors;
        if (filter.speciality().isPresent()) {
            String speciality = filter.speciality().get();
            Validations.validate(speciality);
            doctors = doctorRepository.getBySpeciality(speciality);
        } else {
            doctors = doctorRepository.getAll();
        }

        return DoctorMapper.toDTO(doctors);
    }
    //endregion

}
