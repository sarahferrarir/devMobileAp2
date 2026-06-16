package com.example.devMobile.controller;

import com.example.devMobile.entity.Aula;
import com.example.devMobile.repository.AulaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaRepository aulaRepository;

    public AulaController(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    @GetMapping
    public List<Aula> listarAulas(
            @RequestParam(required = false) String diaSemana,
            @RequestParam(required = false) String turno
    ) {
        if (diaSemana != null && turno != null) {
            return aulaRepository.findByDiaSemanaAndTurno(diaSemana, turno);
        }

        return aulaRepository.findAll();
    }
}