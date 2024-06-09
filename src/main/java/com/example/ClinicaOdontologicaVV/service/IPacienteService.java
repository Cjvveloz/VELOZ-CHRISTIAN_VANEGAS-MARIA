package com.example.ClinicaOdontologicaVV.service;

import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;


import java.util.List;

public interface IPacienteService {

    PacienteSalidaDto registrarPaciente(PacienteEntradaDto pacienteEntradaDto);

    List<PacienteSalidaDto> listarPacientes();

    PacienteSalidaDto buscarPacientePorId(Long id);

    void elimiarPaciente(Long id);

    PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id);
}