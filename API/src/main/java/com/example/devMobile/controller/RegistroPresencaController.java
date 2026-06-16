package com.example.devMobile.controller;

import com.example.devMobile.dto.ResumoInspecaoDTO;
import com.example.devMobile.entity.RegistroPresenca;
import com.example.devMobile.repository.RegistroPresencaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroPresencaController {

    private final RegistroPresencaRepository registroRepository;

    public RegistroPresencaController(RegistroPresencaRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    @GetMapping
    public List<RegistroPresenca> listarRegistros() {
        return registroRepository.findAll();
    }

    @GetMapping("/inspecao/{inspecaoId}")
    public List<RegistroPresenca> listarPorInspecao(@PathVariable Integer inspecaoId) {
        return registroRepository.findByInspecaoId(inspecaoId);
    }

    @PostMapping
    public RegistroPresenca salvarRegistro(@RequestBody RegistroPresenca registro) {
        return registroRepository.save(registro);
    }

    @GetMapping("/resumo/{inspecaoId}")
    public ResumoInspecaoDTO resumo(@PathVariable Integer inspecaoId) {

        List<RegistroPresenca> registros =
                registroRepository.findByInspecaoId(inspecaoId);

        int total = registros.size();

        int presentes = 0;
        int ausentesComJustificativa = 0;
        int ausentesSemJustificativa = 0;

        for (RegistroPresenca registro : registros) {

            if ("PRESENTE".equals(registro.getStatus())) {
                presentes++;
            }

            if ("AUSENTE_COM_JUSTIFICATIVA".equals(registro.getStatus())) {
                ausentesComJustificativa++;
            }

            if ("AUSENTE_SEM_JUSTIFICATIVA".equals(registro.getStatus())) {
                ausentesSemJustificativa++;
            }
        }

        ResumoInspecaoDTO dto = new ResumoInspecaoDTO();

        dto.setTotal(total);
        dto.setPresentes(presentes);
        dto.setAusentesComJustificativa(ausentesComJustificativa);
        dto.setAusentesSemJustificativa(ausentesSemJustificativa);

        if (total > 0) {
            dto.setPercentualFaltas(
                    ((double) (ausentesComJustificativa + ausentesSemJustificativa) / total) * 100
            );

            dto.setPercentualFaltasSemJustificativa(
                    ((double) ausentesSemJustificativa / total) * 100
            );
        } else {
            dto.setPercentualFaltas(0.0);
            dto.setPercentualFaltasSemJustificativa(0.0);
        }

        return dto;
    }
}