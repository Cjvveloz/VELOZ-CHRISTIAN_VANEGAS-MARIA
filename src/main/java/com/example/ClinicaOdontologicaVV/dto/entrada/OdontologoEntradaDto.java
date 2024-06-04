package com.example.ClinicaOdontologicaVV.dto.entrada;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class OdontologoEntradaDto {

    @Digits(message = "Debe especificar el numero de matricula de odontologo", integer = 10, fraction = 0)
    @Positive(message = "La matricula no puede ser negativa")
    private int numeroMatricula;
    @NotBlank (message = "el nombre no debe ir en blanco")
    private String nombre;
    @NotBlank(message = "el apellido no denbe ir en blanco")
    private String apellido;

    public OdontologoEntradaDto(int numeroMatricula, String nombre, String apellido) {

        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}