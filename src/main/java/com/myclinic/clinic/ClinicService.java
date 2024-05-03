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

        List<Clinic> clinics;

        if (filter.location().isPresent()) {
            if (filter.speciality().isPresent()) {
                clinics = clinicRepository.getClinicsByLocationAndSpeciality(filter.location().get(), filter.speciality().get());
            } else
                clinics = clinicRepository.getClinicsByLocation(filter.location().get());
        } else if (filter.speciality().isPresent())
            clinics = clinicRepository.getClinicsBySpeciality(filter.speciality().get());
        else {
            clinics = clinicRepository.getAllClinics();
        }
        
        return ClinicMapper.toDTO(clinics);
    }

}
