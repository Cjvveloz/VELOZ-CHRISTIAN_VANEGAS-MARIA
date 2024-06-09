package com.example.ClinicaOdontologicaVV.service.impl;

import com.example.ClinicaOdontologicaVV.dto.entrada.OdontologoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.OdontologoSalidaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.entity.Odontologo;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.example.ClinicaOdontologicaVV.repository.OdontologoRepository;
import com.example.ClinicaOdontologicaVV.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }




    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {

        LOGGER.info("OdontologoEntradaDto: " + odontologoEntradaDto);
        Odontologo odontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        LOGGER.info("OdontologoEntidad: " + odontologo);
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRepository.save(odontologo), OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: " + odontologoSalidaDto);
        return odontologoSalidaDto;

    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologos = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de odontologos: {}", odontologos);

        return odontologos;
    }


    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoEncontrado = null;

        if (odontologoBuscado != null){
            odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}" + odontologoEncontrado);
        }


        return odontologoEncontrado;
    }

    @Override
    public void eliminarOdontologo(Long id) {
        if (buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Odontologo con ID: " + id +" eliminado");
        }else{}
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) {
        Odontologo odontologoRecibido = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        Odontologo odontologoAActualizar = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if (odontologoAActualizar != null){
            odontologoRecibido.setId(odontologoAActualizar.getId());
            odontologoAActualizar = odontologoRecibido;

            odontologoRepository.save(odontologoAActualizar);
            odontologoSalidaDto = modelMapper.map(odontologoAActualizar, OdontologoSalidaDto.class);
            LOGGER.warn("Odontologo actualizado: " + odontologoSalidaDto );
        }else{
            LOGGER.error("No fue posible encontrar Odontologo en la base de datos " );
        }
        return odontologoSalidaDto;
    }


    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getdomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilio));
    }
}