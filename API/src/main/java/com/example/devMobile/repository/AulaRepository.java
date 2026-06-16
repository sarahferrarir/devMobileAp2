package com.example.devMobile.repository;

import com.example.devMobile.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Integer> {

    List<Aula> findByDiaSemanaAndTurno(String diaSemana, String turno);
}