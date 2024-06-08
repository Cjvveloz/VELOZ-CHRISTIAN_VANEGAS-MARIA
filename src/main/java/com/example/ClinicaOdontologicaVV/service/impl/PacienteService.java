package com.example.ClinicaOdontologicaVV.service.impl;

import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.example.ClinicaOdontologicaVV.repository.PacienteRepository;
import com.example.ClinicaOdontologicaVV.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto pacienteEntradaDto) {
        //mapeo de DTO a entidad
        //pacienteIDao.registrar(pacienteEntradaDto);
        LOGGER.info("PacienteEntradaDto: " + pacienteEntradaDto);
        Paciente paciente = modelMapper.map(pacienteEntradaDto, Paciente.class);
        LOGGER.info("PacienteEntidad: " + paciente);
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteRepository.save(paciente), PacienteSalidaDto.class);
        LOGGER.info("PacienteSalidaDto: " + pacienteSalidaDto);
        return pacienteSalidaDto;
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacientes = pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();
        LOGGER.info("Listado de pacientes: {}", pacientes);


        return pacientes;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {

        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;

        if (pacienteBuscado != null){
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}" + pacienteEncontrado);
        }
        return pacienteEncontrado;
    }

    @Override
    public void elimiarPaciente(Long id) {

        if (buscarPacientePorId(id) != null) {
        pacienteRepository.deleteById(id);
        LOGGER.warn("Paciente con ID: " + id +" eliminado");
            }else{}

    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) {

        Paciente pacienteRecibido = modelMapper.map(pacienteEntradaDto, Paciente.class);
        Paciente pacienteAActualizar = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteAActualizar != null){

            pacienteRecibido.setId(pacienteAActualizar.getId());
            pacienteRecibido.getDomicilio().setId(pacienteAActualizar.getDomicilio().getId());
            pacienteAActualizar = pacienteRecibido;

            pacienteRepository.save(pacienteAActualizar);
            pacienteSalidaDto = modelMapper.map(pacienteAActualizar, PacienteSalidaDto.class);
            LOGGER.warn("Paciente actualizado: " +pacienteSalidaDto );
        }else{
            LOGGER.error("No fue posibloe encontrar datos de paciente " );
        }

        return pacienteSalidaDto;
    }

    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getdomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilio));
    }

}