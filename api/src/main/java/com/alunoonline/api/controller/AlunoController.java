package com.alunoonline.api.controller;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    AlunoService service;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Aluno> create(@RequestBody Aluno aluno) {
        Aluno alunoCreated = service.create(aluno);

        return ResponseEntity.status(201).body(alunoCreated);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody Aluno alunoUpdated) {
        Optional<Aluno> optionalAluno = service.findById(id);
        if (optionalAluno.isPresent()) {
            Aluno aluno = optionalAluno.get();

            // Verificar se o nome foi fornecido na requisição
            String nomeAtualizado = alunoUpdated.getNome();
            if (nomeAtualizado != null && !nomeAtualizado.isEmpty()) {
                aluno.setNome(nomeAtualizado);
            }

            // Verificar se o email foi fornecido na requisição
            String emailAtualizado = alunoUpdated.getEmail();
            if (emailAtualizado != null && !emailAtualizado.isEmpty()) {
                aluno.setEmail(emailAtualizado);
            }

            // Verificar se o curso foi fornecido na requisição
            String cursoAtualizado = alunoUpdated.getCurso();
            if (cursoAtualizado != null && !cursoAtualizado.isEmpty()) {
                aluno.setCurso(cursoAtualizado);
            }

            service.save(aluno);
            return ResponseEntity.ok(aluno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
