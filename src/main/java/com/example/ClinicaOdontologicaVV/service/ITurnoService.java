package com.example.ClinicaOdontologicaVV.service;


import com.example.ClinicaOdontologicaVV.dto.entrada.TurnoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.TurnoSalidaDto;
import com.example.ClinicaOdontologicaVV.entity.Turno;
import com.example.ClinicaOdontologicaVV.exceptions.BadRequestException;
import com.example.ClinicaOdontologicaVV.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {

    TurnoSalidaDto registrarTurno (TurnoEntradaDto turnoEntradaDto) throws BadRequestException;
    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto buscarTurnoPorId(Long id);
    void eliminarTurnoPorId(Long id) throws ResourceNotFoundException;
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws ResourceNotFoundException;
}
