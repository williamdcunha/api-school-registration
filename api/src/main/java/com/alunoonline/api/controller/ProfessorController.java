package com.alunoonline.api.controller;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    ProfessorService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Professor> create(@RequestBody Professor professor) {

        Professor professorCreated = service.create(professor);

        return ResponseEntity.status(201).body(professorCreated);
    }
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Professor> update(@PathVariable Long id, @RequestBody Professor professorUpdate){
        Optional<Professor> optionalProfessor = service.findById(id);
        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();

            String nomeAtualizado = professorUpdate.getNome();
            String emailAtualizado = professorUpdate.getEmail();

            //verificar se o nome foi fornecido
            if (nomeAtualizado != null && !nomeAtualizado.isEmpty()) {
                professor.setNome(nomeAtualizado);
            }

            //verificar se o email foi fornecido
            if (emailAtualizado != null && !emailAtualizado.isEmpty()) {
                professor.setEmail(emailAtualizado);
            }

            service.save(professor);
            return ResponseEntity.ok(professor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Professor> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/All")
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }
}
