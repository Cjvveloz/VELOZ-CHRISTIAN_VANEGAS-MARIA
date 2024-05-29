package com.example.ClinicaOdontologicaVV.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Turno {
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo   ;
    private LocalDateTime  fechaYHora;

    public Turno(Paciente paciente, Odontologo odontologo, LocalDateTime fechaYHora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaYHora = fechaYHora;
    }

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaYHora) {
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

    @Override
    public String toString() {
        return  "id: " + id + "-Paciente: "+ paciente + "- Odontologo: " + odontologo + "- FechaYHora=" + fechaYHora;

    }
}
