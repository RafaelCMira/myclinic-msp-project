package com.myclinic.clinic;

import com.myclinic.utils.Validations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClinicService {

    private final ClinicRepository clinicRepository;

    ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    List<ClinicDTO> getClinicsByFilter(ClinicFilterDTO filter) {
        Validations.validate(filter);

        if (filter.location().isPresent()) {
            if (filter.speciality().isPresent()) {
                return getClinicsByLocationAndSpeciality(filter.location().get(), filter.speciality().get());
            } else
                return getClinicsByLocation(filter.location().get());
        }

        if (filter.speciality().isPresent())
            return getClinicsBySpeciality(filter.speciality().get());

        return getAllClinics();
    }

    List<ClinicDTO> getAllClinics() {
        var clinics = clinicRepository.getAllClinics();

        return ClinicMapper.toDTO(clinics);
    }

    List<ClinicDTO> getClinicsByLocation(String location) {
        Validations.validate(location);

        var clinics = clinicRepository.getClinicsByLocation(location);

        return ClinicMapper.toDTO(clinics);
    }

    List<ClinicDTO> getClinicsBySpeciality(String speciality) {
        Validations.validate(speciality);

        var clinics = clinicRepository.getClinicsBySpeciality(speciality);

        return ClinicMapper.toDTO(clinics);
    }

    List<ClinicDTO> getClinicsByLocationAndSpeciality(String location, String speciality) {
        Validations.validate(location, speciality);

        var clinics = clinicRepository.getClinicsByLocationAndSpeciality(location, speciality);

        return ClinicMapper.toDTO(clinics);
    }
}
