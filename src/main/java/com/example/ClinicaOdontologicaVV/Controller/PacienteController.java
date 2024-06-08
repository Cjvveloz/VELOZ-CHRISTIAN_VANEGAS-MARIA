package com.example.ClinicaOdontologicaVV.Controller;

import com.example.ClinicaOdontologicaVV.dto.entrada.PacienteEntradaDto;
import com.example.ClinicaOdontologicaVV.dto.salida.PacienteSalidaDto;
import com.example.ClinicaOdontologicaVV.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    //peticion http json --> @requestody & @responseBody--> java dto --> servicio dto <--> entidad -> persistencia entidad<--> base de datos

    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid  PacienteEntradaDto pacienteEntradaDto){
        return new ResponseEntity<>(pacienteService.registrarPaciente(pacienteEntradaDto), HttpStatus.CREATED);
    }

    //GET

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSalidaDto>>  listarPacientes(){
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
    }

    //POST
    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDto> actualizarPaciente(@RequestBody @Valid PacienteEntradaDto pacienteEntradaDto, @PathVariable Long id){
        return new ResponseEntity<>(pacienteService.actualizarPaciente(pacienteEntradaDto, id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/paciente/eliminar")
    public  ResponseEntity<?> eliminarPaciente(@RequestParam Long id){
        pacienteService.elimiarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado con exito", HttpStatus.NO_CONTENT);

    }

}
