package com.example.ClinicaOdontologicaVV.service;


import com.example.ClinicaOdontologicaVV.entity.Turno;

import java.util.List;

public interface ITurnoService {

    Turno registrarTurno (Turno turno);
    List<Turno> listarTurnos();
}
