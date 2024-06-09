package com.example.ClinicaOdontologicaVV.service.impl;

import com.example.ClinicaOdontologicaVV.entity.Domicilio;
import com.example.ClinicaOdontologicaVV.entity.Odontologo;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.example.ClinicaOdontologicaVV.entity.Turno;
import com.example.ClinicaOdontologicaVV.repository.impl.OdontologoDaoH2;
import com.example.ClinicaOdontologicaVV.repository.impl.PacienteDaoH2;
import com.example.ClinicaOdontologicaVV.repository.impl.TurnoDaoH2;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/*
class TurnoServiceTest {
    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Test
    void deberiaRegistrarseUnTurnoEnLaBaseDeDatos(){

        pacienteService = new PacienteService(new PacienteDaoH2());
        Paciente paciente = new Paciente("Frank", "Valverde", 123456, LocalDate.of(2023, 05, 02), new Domicilio("Calle", 13, "Localidad", "Provincia"));
        Paciente pacienteRegistrado = pacienteService.registrarPaciente(paciente);

        odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologo = new Odontologo(437,"Victor","Veloz");
        Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);

        turnoService = new TurnoService(new TurnoDaoH2());
        LocalDateTime fechaHora = LocalDateTime.of(2024, 5, 28, 0, 0);

        Turno turno = new Turno(pacienteRegistrado,odontologoRegistrado,fechaHora);

        Turno turnoRegistrado = turnoService.registrarTurno(turno);

        assertNotNull(turnoRegistrado);
        assertEquals(11L, turnoRegistrado.getPaciente().getId());
        assertEquals(12L, turnoRegistrado.getOdontologo().getId());
        assertEquals(fechaHora, turnoRegistrado.getFechaYHora());


    }


}