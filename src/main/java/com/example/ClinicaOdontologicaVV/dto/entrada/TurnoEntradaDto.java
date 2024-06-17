package com.example.ClinicaOdontologicaVV.dto.entrada;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TurnoEntradaDto {
    @NotNull(message = "paciente no debe ir nulo")
    @Valid
    private Long paciente;
    @NotNull(message = "paciente no debe ir nulo")
    @Valid
    private Long odontologo ;
    @FutureOrPresent(message = "el turno se debe agendar para hoy o dias futuros")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaYHora;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(Long paciente, Long odontologo, LocalDateTime fechaYHora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaYHora = fechaYHora;
    }

    public Long getPaciente() {
        return paciente;
    }

    public void setPaciente(Long paciente) {
        this.paciente = paciente;
    }

    public Long getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Long odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime  getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime  fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public String getFechaYHoraAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaYHora.format(formatter);
    }

    // Método para establecer la fecha y hora desde una cadena
    public void setFechaYHoraFromString(String fechaYHoraStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.fechaYHora = LocalDateTime.parse(fechaYHoraStr, formatter);
    }


}
