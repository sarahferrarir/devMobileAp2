package com.example.devMobile.repository;

import com.example.devMobile.entity.RegistroPresenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPresencaRepository extends JpaRepository<RegistroPresenca, Integer> {

    List<RegistroPresenca> findByInspecaoId(Integer inspecaoId);
}