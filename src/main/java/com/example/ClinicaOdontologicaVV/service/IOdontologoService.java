package com.example.ClinicaOdontologicaVV.service;

import com.example.ClinicaOdontologicaVV.dto.entrada.OdontologoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.OdontologoSalidaDto;
import com.example.ClinicaOdontologicaVV.exceptions.ResourceNotFoundException;


import java.util.List;

public interface IOdontologoService {

    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
    OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) throws ResourceNotFoundException;




}