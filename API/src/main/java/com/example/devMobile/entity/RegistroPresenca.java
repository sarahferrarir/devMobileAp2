package com.example.devMobile.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "registros_presenca")
public class RegistroPresenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inspecao_id")
    private Integer inspecaoId;

    @Column(name = "aula_id")
    private Integer aulaId;

    private String status;

    private String observacao;

    public RegistroPresenca() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspecaoId() {
        return inspecaoId;
    }

    public void setInspecaoId(Integer inspecaoId) {
        this.inspecaoId = inspecaoId;
    }

    public Integer getAulaId() {
        return aulaId;
    }

    public void setAulaId(Integer aulaId) {
        this.aulaId = aulaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}