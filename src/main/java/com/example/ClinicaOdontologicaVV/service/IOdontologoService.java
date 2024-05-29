package com.example.ClinicaOdontologicaVV.service;

import com.example.ClinicaOdontologicaVV.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {

    Odontologo registrarOdontologo(Odontologo odontologo);
    List<Odontologo> listarOdontologo();

}