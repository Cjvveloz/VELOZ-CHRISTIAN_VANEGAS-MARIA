package com.example.ClinicaOdontologicaVV.dto.entrada;


import com.fasterxml.jackson.annotation.JsonFormat;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class PacienteEntradaDto {
    @NotBlank(message = "Debe ingresar nombre de paciente")
    @Size(max = 40, message = "El nombre solo permite 40 caracteres")
    @Pattern(regexp = "[a-zA-Z]+", message = "El nombre solo puede contener letras")
    private String nombre;

    @NotBlank(message = "Debe ingresar apellido de paciente")
    @Size(max = 40, message = "El apellido solo permite 40 caracteres")
    @Pattern(regexp = "[a-zA-Z]+", message = "El nombre solo puede contener letras")
    private String apellido;

    @Positive(message = "el dni del paciente debe no debe ser nulo ni negativo")
    private int dni;

    @FutureOrPresent(message = "La fecha de ingreso no puede ser menor al dia de hoy")
    @NotNull(message = "Debe ingresar los datos de fecha ingreso del paciente")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @NotNull(message = "el domicilio no debe ser nulo")
    @Valid
    private DomicilioEntradaDto domicilioEntradaDto;

    public PacienteEntradaDto() {
    }

    public PacienteEntradaDto(String nombre, String apellido, int dni, LocalDate fechaIngreso, DomicilioEntradaDto domicilioEntradaDto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilioEntradaDto = domicilioEntradaDto;
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioEntradaDto getdomicilioEntradaDto() {
        return domicilioEntradaDto;
    }

    public void setdomicilioEntradaDto(DomicilioEntradaDto domicilioEntradaDto) {
        this.domicilioEntradaDto = domicilioEntradaDto;
    }


}