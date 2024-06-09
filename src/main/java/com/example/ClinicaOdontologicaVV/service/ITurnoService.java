package com.example.ClinicaOdontologicaVV.service;


import com.example.ClinicaOdontologicaVV.dto.entrada.TurnoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.TurnoSalidaDto;
import com.example.ClinicaOdontologicaVV.entity.Turno;

import java.util.List;

public interface ITurnoService {

    TurnoSalidaDto registrarTurno (TurnoEntradaDto turnoEntradaDto);
    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto buscarTurnoPorId(Long id);
    void eliminarTurnoPorId(Long id);
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id);
}
