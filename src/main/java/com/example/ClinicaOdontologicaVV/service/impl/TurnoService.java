package com.example.ClinicaOdontologicaVV.service.impl;

import com.example.ClinicaOdontologicaVV.entity.Turno;
import com.example.ClinicaOdontologicaVV.repository.IDao;
import com.example.ClinicaOdontologicaVV.service.ITurnoService;

import java.util.List;

public class TurnoService implements ITurnoService {

    private IDao<Turno> turnoIDao;

    public TurnoService(IDao<Turno> turnoIDao) {
        this.turnoIDao = turnoIDao;
    }

    @Override
    public Turno registrarTurno(Turno turno) {
        return turnoIDao.registrar(turno);
    }

    @Override
    public List<Turno> listarTurnos() {
        return turnoIDao.listarTodos();
    }
}
