package com.example.ClinicaOdontologicaVV.service.impl;

import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.TurnoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.TurnoSalidaDto;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.example.ClinicaOdontologicaVV.entity.Turno;
import com.example.ClinicaOdontologicaVV.repository.TurnoRepository;
import com.example.ClinicaOdontologicaVV.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }




    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) {

        LOGGER.info("TurnoEntradaDto: " + turnoEntradaDto);
        Turno turno = modelMapper.map(turnoEntradaDto, Turno.class);
        LOGGER.info("TurnoEntidad: " + turno);
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRepository.save(turno), TurnoSalidaDto.class);
        LOGGER.info("TurnoSalidaDto: " + turnoSalidaDto);
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de Turnos: {}", turnos);
        return turnos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;
        if (turnoBuscado != null){
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}" + turnoEncontrado);
        }
        return turnoEncontrado;
    }

    @Override
    public void eliminarTurnoPorId(Long id) {
        if (buscarTurnoPorId(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Turno con ID: " + id +" eliminado");
        }else{}
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {

        Turno turnoRecibido = modelMapper.map(turnoEntradaDto, Turno.class);
        Turno turnoAActualizar = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoAActualizar != null) {
            // Actualizar campos específicos de turnoAActualizar
            turnoAActualizar.setFechaYHora(turnoRecibido.getFechaYHora());

            // Actualizar referencias a paciente y odontologo si es necesario
            if (turnoRecibido.getPaciente() != null) {
                turnoAActualizar.setPaciente(turnoRecibido.getPaciente());
            }

            if (turnoRecibido.getOdontologo() != null) {
                turnoAActualizar.setOdontologo(turnoRecibido.getOdontologo());
            }

            turnoRepository.save(turnoAActualizar);
            turnoSalidaDto = modelMapper.map(turnoAActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: " + turnoSalidaDto);
        } else {
            LOGGER.error("No fue posible encontrar datos de turno con el ID: " + id);
        }

        return turnoSalidaDto;
    }

    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getdomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilio));
    }
}
