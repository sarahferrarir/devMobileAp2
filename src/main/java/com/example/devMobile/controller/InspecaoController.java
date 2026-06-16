package com.example.devMobile.controller;

import com.example.devMobile.entity.Inspecao;
import com.example.devMobile.repository.InspecaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inspecoes")
public class InspecaoController {

    private final InspecaoRepository inspecaoRepository;

    public InspecaoController(InspecaoRepository inspecaoRepository) {
        this.inspecaoRepository = inspecaoRepository;
    }

    @GetMapping
    public List<Inspecao> listarInspecoes() {
        return inspecaoRepository.findAll();
    }

    @PostMapping
    public Inspecao criarInspecao(@RequestBody Inspecao inspecao) {
        inspecao.setFinalizada(false);
        return inspecaoRepository.save(inspecao);
    }

    @PutMapping("/{id}/finalizar")
    public Inspecao finalizarInspecao(@PathVariable Integer id) {
        Inspecao inspecao = inspecaoRepository.findById(id).orElseThrow();
        inspecao.setFinalizada(true);
        return inspecaoRepository.save(inspecao);
    }
}