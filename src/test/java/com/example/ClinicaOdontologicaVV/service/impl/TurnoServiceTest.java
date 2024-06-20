package com.example.ClinicaOdontologicaVV.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.example.ClinicaOdontologicaVV.dto.entrada.OdontologoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.TurnoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.OdontologoSalidaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.TurnoSalidaDto;
import com.example.ClinicaOdontologicaVV.exceptions.BadRequestException;

import com.example.ClinicaOdontologicaVV.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    private Long pacienteId;
    private Long odontologoId;

    @BeforeEach
    void setUp() {

        PacienteEntradaDto pacienteDto = new PacienteEntradaDto(null, "Juan", "Perez", 12345678, LocalDateTime.now().toLocalDate(), null);
        PacienteSalidaDto pacienteRegistrado = pacienteService.registrarPaciente(pacienteDto);
        pacienteId = pacienteRegistrado.getId();


        OdontologoEntradaDto odontologoDto = new OdontologoEntradaDto(null, 98765432, "María", "González");
        OdontologoSalidaDto odontologoRegistrado = odontologoService.registrarOdontologo(odontologoDto);
        odontologoId = odontologoRegistrado.getId();
    }

    @Test
    @Order(1)
    public void deberiaRegistrarUnTurnoYRetornarSuId() {
        TurnoEntradaDto turnoDto = new TurnoEntradaDto(pacienteId, odontologoId, LocalDateTime.now().plusDays(1));

        TurnoSalidaDto turnoRegistrado = null;
        try {
            turnoRegistrado = turnoService.registrarTurno(turnoDto);
        } catch (BadRequestException e) {
            fail("Error al registrar el turno: " + e.getMessage());
        }

        assertNotNull(turnoRegistrado);
        assertNotNull(turnoRegistrado.getFechaYHora());
        assertEquals(pacienteId, turnoRegistrado.getPaciente().getId());
        assertEquals(odontologoId, turnoRegistrado.getOdontologo().getId());
    }

    @Test
    @Order(2)
    public void deberiaActualizarElTurnoConIdExistente() throws ResourceNotFoundException {

        TurnoEntradaDto turnoDto = new TurnoEntradaDto(pacienteId, odontologoId, LocalDateTime.now().plusDays(2));
        TurnoSalidaDto turnoRegistrado = null;
        try {
            turnoRegistrado = turnoService.registrarTurno(turnoDto);
        } catch (BadRequestException e) {
            fail("Error al registrar el turno para actualizar: " + e.getMessage());
        }

        assertNotNull(turnoRegistrado);


        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(3);
        turnoDto.setFechaYHora(nuevaFecha);


        TurnoSalidaDto turnoActualizado = turnoService.actualizarTurno(turnoDto, turnoRegistrado.getPaciente().getId());

        assertNotNull(turnoActualizado);
        assertEquals(nuevaFecha, turnoActualizado.getFechaYHora());
    }

    @Test
    @Order(3)
    public void deberiaListarTodosLosTurnos() {

        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        assertNotNull(turnos);
        assertFalse(turnos.isEmpty());
    }


}
