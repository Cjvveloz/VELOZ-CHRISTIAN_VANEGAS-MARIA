package com.example.ClinicaOdontologicaVV.service.impl;

import com.example.ClinicaOdontologicaVV.dto.entrada.OdontologoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.TurnoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.TurnoSalidaDto;
import com.example.ClinicaOdontologicaVV.entity.Odontologo;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.example.ClinicaOdontologicaVV.entity.Turno;
import com.example.ClinicaOdontologicaVV.exceptions.BadRequestException;
import com.example.ClinicaOdontologicaVV.exceptions.ResourceNotFoundException;
import com.example.ClinicaOdontologicaVV.repository.OdontologoRepository;
import com.example.ClinicaOdontologicaVV.repository.PacienteRepository;
import com.example.ClinicaOdontologicaVV.repository.TurnoRepository;
import com.example.ClinicaOdontologicaVV.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }





    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        LOGGER.info("TurnoEntradaDto: " + turnoEntradaDto);


        Paciente paciente = pacienteRepository.findById(turnoEntradaDto.getPaciente())
                .orElseThrow(() -> new BadRequestException("Paciente no encontrado con ID: " + turnoEntradaDto.getPaciente()));


        Odontologo odontologo = odontologoRepository.findById(turnoEntradaDto.getOdontologo())
                .orElseThrow(() -> new BadRequestException("Odontólogo no encontrado con ID: " + turnoEntradaDto.getOdontologo()));


        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFechaYHora(turnoEntradaDto.getFechaYHora());

        LOGGER.info("TurnoEntidad a guardar: " + turno);

        // Guardar el turno en el repositorio y mapear a TurnoSalidaDto
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRepository.save(turno), TurnoSalidaDto.class);
        LOGGER.info("TurnoSalidaDto creado: " + turnoSalidaDto);

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
    public void eliminarTurnoPorId(Long id) throws ResourceNotFoundException {
        if (buscarTurnoPorId(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Turno con ID: " + id +" eliminado");
        }else{
            throw  new ResourceNotFoundException("No existe registro de Turno con ese id  " + id);
        }
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

    private void configureMapping() {

        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> {
                    mapper.map(PacienteEntradaDto::getId, Paciente::setId);

                });


        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class)
                .addMappings(mapper -> {
                    mapper.map(OdontologoEntradaDto::getId, Odontologo::setId);

                });
    }
}
