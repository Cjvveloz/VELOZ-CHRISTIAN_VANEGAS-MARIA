package com.example.ClinicaOdontologicaVV.dto.salida;

import com.example.ClinicaOdontologicaVV.entity.Odontologo;
import com.example.ClinicaOdontologicaVV.entity.Paciente;

import java.time.LocalDateTime;

public class TurnoSalidaDto {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo   ;
    private LocalDateTime  fechaYHora;

    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaYHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaYHora = fechaYHora;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime  getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime  fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

}
