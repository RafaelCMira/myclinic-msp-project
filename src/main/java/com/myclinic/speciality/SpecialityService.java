package com.myclinic.speciality;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SpecialityService {

    private final SpecialityRepository specialityRepository;

    SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    //region Get
    List<SpecialityDTO> getSpecialities() {
        var specialities = specialityRepository.getAll();
        return SpecialityMapper.toDTO(specialities);
    }
    //endregion


}
