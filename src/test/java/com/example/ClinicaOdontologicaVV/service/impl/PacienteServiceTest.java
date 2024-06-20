package com.example.ClinicaOdontologicaVV.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ClinicaOdontologicaVV.dto.entrada.DomicilioEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:applicationTest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;


    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreJuan_yRetornarSuId(){

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto(1l,"Christian", "Veloz", 90909090, LocalDate.of(2024, 6, 25), new DomicilioEntradaDto("Balzar", 17, "Guayaquil", "Guayas"));

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        //assert
        assertNotNull(pacienteSalidaDto);
        assertEquals("Christian", pacienteSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDePacientes(){
        List<PacienteSalidaDto> listadoDePacientes = pacienteService.listarPacientes();
        assertFalse(listadoDePacientes.isEmpty());
    }



    @Test
    @Order(3)
    void deberiaActualizarElPacienteConId1() throws ResourceNotFoundException {
        DomicilioEntradaDto domicilio = new DomicilioEntradaDto("Calle Nueva", 123, "Quito", "Pichincha");
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto(1L, "Juan", "Perez", 12345678, LocalDate.of(2024, 6, 30), domicilio);

        PacienteSalidaDto pacienteSalidaDto = pacienteService.actualizarPaciente(pacienteEntradaDto, 1L);

        // assert
        assertNotNull(pacienteSalidaDto);
        assertEquals("Juan", pacienteSalidaDto.getNombre());
        assertEquals("Perez", pacienteSalidaDto.getApellido());
        assertEquals("Quito", pacienteSalidaDto.getDomicilioSalidaDto().getLocalidad());
    }


    @Test
    @Order(4)
    void deberiaEncontrarElPacienteConId1() {
        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(1L);

        // assert
        assertNotNull(pacienteSalidaDto);
        assertEquals(1L, pacienteSalidaDto.getId());
    }
    @Test
    @Order(5)
    void deberiaEliminarseElPacienteConId1(){

        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(6)
    void deberiaDevolverUnaListaVaciaDePacientes(){
        List<PacienteSalidaDto> listadoDePacientes = pacienteService.listarPacientes();
        assertTrue(listadoDePacientes.isEmpty());
    }


    @Test
    @Order(7)
    void deberiaLanzarExcepcionAlEliminarPacienteConIdInexistente() {
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(999L));
    }

}
