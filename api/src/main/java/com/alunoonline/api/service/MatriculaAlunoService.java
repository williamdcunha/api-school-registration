package com.alunoonline.api.service;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.dtos.PatchGradesRequestDto;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatriculaAlunoService {

    static final Double gradesAvgToApprove = 7.00;
    @Autowired
    MatriculaAlunoRepository repository;

    public MatriculaAluno create(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus("MATRICULADO");
        return repository.save(matriculaAluno);
    }

    public MatriculaAluno save(MatriculaAluno matriculaAluno) {
        return repository.save(matriculaAluno);
    }

    public Optional<MatriculaAluno> findById(Long id) {
        return repository.findById(id);
    }

    public void patchGrades(PatchGradesRequestDto patchGradesRequestDto, Long matriculaAlunoId) {
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        boolean needUpdate = false;
        if (patchGradesRequestDto.getNota1() != null) {
            matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setNota1(patchGradesRequestDto.getNota1()));
            needUpdate = true;
        }
        if (patchGradesRequestDto.getNota2() != null) {
            matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setNota2(patchGradesRequestDto.getNota2()));
            needUpdate = true;
        }

        if (needUpdate) {
            if (matriculaAlunoToUpdate.get().getNota1() != null && matriculaAlunoToUpdate.get().getNota2() != null) {
                if ((matriculaAlunoToUpdate.get().getNota1() + matriculaAlunoToUpdate.get().getNota2()) / 2 >= gradesAvgToApprove) {
                    matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("APROVADO"));
                } else {
                    matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("REPROVADO"));
                }
            }
            repository.save(matriculaAlunoToUpdate.get());
        }
    }
}
