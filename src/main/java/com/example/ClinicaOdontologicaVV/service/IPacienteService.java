package com.example.ClinicaOdontologicaVV.service;

import com.example.ClinicaOdontologicaVV.entity.Paciente;

import java.util.List;

public interface IPacienteService {

    Paciente registrarPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
}