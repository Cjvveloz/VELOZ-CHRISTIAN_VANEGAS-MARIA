package com.example.ClinicaOdontologicaVV.repository;

import com.example.ClinicaOdontologicaVV.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
