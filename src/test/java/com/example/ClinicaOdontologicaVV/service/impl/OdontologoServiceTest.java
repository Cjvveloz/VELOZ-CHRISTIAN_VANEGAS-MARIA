package com.example.ClinicaOdontologicaVV.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.example.ClinicaOdontologicaVV.dto.entrada.OdontologoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.OdontologoSalidaDto;

import com.example.ClinicaOdontologicaVV.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void deberiaRegistrarseUnOdontologoDeNombreLaura_yRetornarSuId() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(1L, 234567890, "Laura", "Martinez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        // assert
        assertNotNull(odontologoSalidaDto);
        assertEquals("Laura", odontologoSalidaDto.getNombre());
        assertEquals("Martinez", odontologoSalidaDto.getApellido());
    }

    @Test
    @Order(2)
    public void deberiaActualizarElOdontologoConId1() throws ResourceNotFoundException {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(1L, 345678902, "Pedro", "Gonzalez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.actualizarOdontologo(odontologoEntradaDto, 1L);

        // assert
        assertNotNull(odontologoSalidaDto);
        assertEquals("Pedro", odontologoSalidaDto.getNombre());
        assertEquals("Gonzalez", odontologoSalidaDto.getApellido());
    }

    @Test
    @Order(3)
    public void deberiaEncontrarElOdontologoConId1() {
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(1L);

        // assert
        assertNotNull(odontologoSalidaDto);
        assertEquals(1L, odontologoSalidaDto.getId());
        assertEquals("Pedro", odontologoSalidaDto.getNombre());
        assertEquals("Gonzalez", odontologoSalidaDto.getApellido());
    }
}
