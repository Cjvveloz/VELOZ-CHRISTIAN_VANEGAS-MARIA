package com.example.ClinicaOdontologicaVV.repository.impl;

import com.example.ClinicaOdontologicaVV.dbconnection.H2Connection;
import com.example.ClinicaOdontologicaVV.entity.Odontologo;
import com.example.ClinicaOdontologicaVV.entity.Paciente;
import com.example.ClinicaOdontologicaVV.entity.Turno;
import com.example.ClinicaOdontologicaVV.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDaoH2 implements IDao<Turno> {
    private final Logger LOGGER =  LoggerFactory.getLogger(TurnoDaoH2.class);




    @Override
    public Turno registrar(Turno turno) {
        Connection connection = null;
        Turno turnoRegistrado = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TURNOS (PACIENTES_ID, ODONTOLOGOS_ID, FECHAYHORA) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, turno.getPaciente().getId());
            preparedStatement.setLong(2, turno.getOdontologo().getId());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(turno.getFechaYHora()));
            preparedStatement.execute();

            turnoRegistrado = new Turno(turno.getPaciente(), turno.getOdontologo(),turno.getFechaYHora());

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()) {
                turnoRegistrado.setId(resultSet.getLong("id"));
            }

            connection.commit();

            LOGGER.info("Se ha registrado el turno: " + turnoRegistrado);



        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }


        return turnoRegistrado;
    }

    @Override
    public Turno buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<Turno> listarTodos() {
        Connection connection = null;
        List<Turno> turnos = new ArrayList<>();
        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TURNOS");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                Turno turno = crearObjetoTurno(resultSet);
                turnos.add(turno);
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        LOGGER.info("Listado de todos los Turnos: " + turnos);

        return turnos;
    }

    private Turno crearObjetoTurno(ResultSet resultSet) throws SQLException {

        Paciente paciente = new PacienteDaoH2().buscarPorId(resultSet.getLong("paciente_id"));
        Odontologo odontologo = new OdontologoDaoH2().buscarPorId(resultSet.getLong("odontologo_id"));
        return new Turno(resultSet.getLong("id"), paciente, odontologo, resultSet.getTimestamp("fechayhora").toLocalDateTime());
    }

}


