package com.example.ClinicaOdontologicaVV.dto.entrada;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class OdontologoEntradaDto {

    private Long id;
    @Digits(message = "Debe especificar el numero de matricula de odontologo", integer = 10, fraction = 0)
    @Positive(message = "La matricula no puede ser negativa")
    private int numeroMatricula;
    @NotBlank(message = "el nombre no debe ir en blanco")
    @Pattern(regexp = "[a-zA-Z]+", message = "El nombre solo puede contener letras")
    private String nombre;
    @NotBlank(message = "el apellido no denbe ir en blanco")
    @Pattern(regexp = "[a-zA-Z]+", message = "El nombre solo puede contener letras")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(Long id, int numeroMatricula, String nombre, String apellido) {
        this.id = id;
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public OdontologoEntradaDto(int numeroMatricula, String nombre, String apellido) {

        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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