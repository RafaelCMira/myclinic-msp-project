package com.myclinic.patient;

import com.myclinic.exception.customexceptions.NotFoundException;
import com.myclinic.utils.Validations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PatientService {

    private final PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //region Insert
    PatientDTO insertPatient(PatientDTO patientDTO) {
        Validations.validate(patientDTO);

        var patient = PatientMapper.fromDTO(patientDTO);
        var patientId = patientRepository.insertPatient(patient);

        if (patientId.isEmpty())
            throw new NotFoundException(PatientErrorMessages.INSERTING_PATIENT_DB_ERROR.formatMsg(patientDTO));

        return PatientDTO.withId(patientDTO, patientId.get());
    }
    //endregion

    //region Update
    PatientDTO updatePatient(Integer patientId, PatientDTO patientDTO) {
        Validations.validate(patientDTO);

        var patient = PatientMapper.fromDTO(patientDTO);
        patient.setId(patientId);

        var res = patientRepository.updatePatient(patient);

        if (res == 0) {
            throw new NotFoundException(PatientErrorMessages.PATIENT_NOT_FOUND.formatMsg(patientId));
        }

        return PatientMapper.toDTO(patient);
    }
    //endregion

    //region Delete
    void deletePatient(Integer patientId) {
        var res = patientRepository.deletePatient(patientId);

        if (res == 0) {
            throw new NotFoundException(PatientErrorMessages.PATIENT_NOT_FOUND.formatMsg(patientId));
        }
    }
    //endregion

    //region Get

    PatientDTO getPatient(Integer patientId) {
        var patientInfo = patientRepository.findById(patientId);

        var patient = patientInfo.orElseThrow(
                () -> new NotFoundException(PatientErrorMessages.PATIENT_NOT_FOUND.formatMsg(patientId))
        );

        return PatientMapper.toDTO(patient);
    }

    List<PatientDTO> getAll() {
        var patients = patientRepository.getALL();
        return PatientMapper.toDTO(patients);
    }

    //endregion
}
