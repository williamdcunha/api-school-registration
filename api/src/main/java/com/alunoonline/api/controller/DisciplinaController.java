package com.alunoonline.api.controller;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {
    @Autowired
    DisciplinaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Disciplina> create(@RequestBody Disciplina disciplina){
        Disciplina disciplinaCreated = service.create(disciplina);
        return ResponseEntity.status(201).body(disciplinaCreated);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Disciplina> findById(@PathVariable Long id) {
        return service.findById(id);
    }
    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> findAll(){
        return service.findAll();
    }

    @GetMapping("/professor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> findByProfessorId(@PathVariable Long id){
        return service.findByProfessorId(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Disciplina> update(@PathVariable Long id, @RequestBody Disciplina disciplinaUpdate){
        Optional<Disciplina> optionalDisciplina = service.findById(id);
        if (optionalDisciplina.isPresent()){
            Disciplina disciplina = optionalDisciplina.get();

            // Verificar se o nome foi fornecido na requisição
            String nomeAtualizado = disciplinaUpdate.getNome();
            if (nomeAtualizado != null && !nomeAtualizado.isEmpty()) {
                disciplina.setNome(nomeAtualizado);
            }

            // Verificar se o professor foi fornecido na requisição
            Professor professorAtualizado = disciplinaUpdate.getProfessor();
            if (professorAtualizado != null) {
                disciplina.setProfessor(professorAtualizado);
            }

            service.save(disciplina);
            return ResponseEntity.ok(disciplina);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) { service.deleteById(id); }
}


















