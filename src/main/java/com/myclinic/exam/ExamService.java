package com.myclinic.exam;

import com.myclinic.exception.customexceptions.AlreadyExistsException;
import com.myclinic.utils.Validations;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ExamService {

    private final ExamRepository examRepository;

    ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    //region Insert
    ExamDTO insertExam(ExamDTO examDTO) {
        Validations.validate(examDTO);

        var exam = ExamMapper.fromDTO(examDTO);

        try {
            examRepository.insertExam(exam);
        } catch (DataAccessException e) {
            throw new AlreadyExistsException(ExamErrorMessages.EXAM_ALREADY_EXISTS.formatMsg(examDTO));
        }

        return ExamMapper.toDTO(exam);
    }
    //endregion


    //region Get
    List<ExamDTO> getExams(
            Optional<Integer> patientId,
            Optional<Integer> clinicId,
            Optional<String> date,
            Optional<String> hour,
            Optional<String> motive) {

        var exams = examRepository.getByFilter(patientId, clinicId, date, hour, motive);

        return ExamMapper.toDTO(exams);
    }

    //endregion
}
