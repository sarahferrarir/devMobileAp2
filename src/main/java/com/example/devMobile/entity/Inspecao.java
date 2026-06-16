package com.example.devMobile.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inspecoes")
public class Inspecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inspecao")
    private LocalDate dataInspecao;

    @Column(name = "dia_semana")
    private String diaSemana;

    private String turno;

    @Column(name = "inspetor_id")
    private Integer inspetorId;

    private Boolean finalizada;

    public Inspecao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataInspecao() {
        return dataInspecao;
    }

    public void setDataInspecao(LocalDate dataInspecao) {
        this.dataInspecao = dataInspecao;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getInspetorId() {
        return inspetorId;
    }

    public void setInspetorId(Integer inspetorId) {
        this.inspetorId = inspetorId;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }
}