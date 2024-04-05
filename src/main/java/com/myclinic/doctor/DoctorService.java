package com.myclinic.doctor;

import com.myclinic.exception.customexceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
class DoctorService {

    private final DoctorRepository doctorRepository;

    DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    DoctorDTO getDoctor(Integer doctorId) {
        var doctorInfo = doctorRepository.findById(doctorId);

        var doctor = doctorInfo.orElseThrow(
                () -> new NotFoundException("Doctor not found")
        );

        return DoctorMapper.toDTO(doctor);
    }

    List<DoctorDTO> getAll() {
        var doctors = doctorRepository.getAll();
        return DoctorMapper.toDTO(doctors);
    }


}
