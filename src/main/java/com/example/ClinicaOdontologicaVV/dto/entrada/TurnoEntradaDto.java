package com.example.ClinicaOdontologicaVV.dto.entrada;

import com.example.ClinicaOdontologicaVV.entity.Odontologo;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @NotNull(message = "paciente no debe ir nulo")
    @Valid
    private PacienteEntradaDto paciente;
    @NotNull(message = "paciente no debe ir nulo")
    @Valid
    private OdontologoEntradaDto odontologo ;
    @FutureOrPresent(message = "el turno se debe agendar para hoy o dias futuros")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDateTime  fechaYHora;



    public TurnoEntradaDto(Long id, PacienteEntradaDto paciente, OdontologoEntradaDto odontologo, LocalDateTime fechaYHora) {

        this.paciente = paciente;
        this. odontologo = odontologo;
        this.fechaYHora = fechaYHora;
    }





    public PacienteEntradaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntradaDto paciente) {
        this.paciente = paciente;
    }

    public OdontologoEntradaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoEntradaDto odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime  getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime  fechaYHora) {
        this.fechaYHora = fechaYHora;
    }


}
