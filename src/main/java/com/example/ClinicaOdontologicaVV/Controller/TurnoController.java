package com.example.ClinicaOdontologicaVV.Controller;

import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.entrada.TurnoEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.TurnoSalidaDto;
import com.example.ClinicaOdontologicaVV.service.IPacienteService;
import com.example.ClinicaOdontologicaVV.service.ITurnoService;
import com.example.ClinicaOdontologicaVV.service.impl.TurnoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("Turnos")
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto){
        return new ResponseEntity<>(turnoService.registrarTurno(turnoEntradaDto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>>  listarTurnos(){
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto, @PathVariable Long id){
        return new ResponseEntity<>(turnoService.actualizarTurno(turnoEntradaDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/turno/eliminar")
    public  ResponseEntity<?> eliminarTurno(@RequestParam Long id){
        turnoService.eliminarTurnoPorId(id);
        return new ResponseEntity<>("Turno eliminado con exito", HttpStatus.NO_CONTENT);

    }

}
