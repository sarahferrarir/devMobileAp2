package com.example.devMobile.dto;

public class ResumoInspecaoDTO {

    private Integer total;
    private Integer presentes;
    private Integer ausentesComJustificativa;
    private Integer ausentesSemJustificativa;

    private Double percentualFaltas;
    private Double percentualFaltasSemJustificativa;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPresentes() {
        return presentes;
    }

    public void setPresentes(Integer presentes) {
        this.presentes = presentes;
    }

    public Integer getAusentesComJustificativa() {
        return ausentesComJustificativa;
    }

    public void setAusentesComJustificativa(Integer ausentesComJustificativa) {
        this.ausentesComJustificativa = ausentesComJustificativa;
    }

    public Integer getAusentesSemJustificativa() {
        return ausentesSemJustificativa;
    }

    public void setAusentesSemJustificativa(Integer ausentesSemJustificativa) {
        this.ausentesSemJustificativa = ausentesSemJustificativa;
    }

    public Double getPercentualFaltas() {
        return percentualFaltas;
    }

    public void setPercentualFaltas(Double percentualFaltas) {
        this.percentualFaltas = percentualFaltas;
    }

    public Double getPercentualFaltasSemJustificativa() {
        return percentualFaltasSemJustificativa;
    }

    public void setPercentualFaltasSemJustificativa(Double percentualFaltasSemJustificativa) {
        this.percentualFaltasSemJustificativa = percentualFaltasSemJustificativa;
    }
}