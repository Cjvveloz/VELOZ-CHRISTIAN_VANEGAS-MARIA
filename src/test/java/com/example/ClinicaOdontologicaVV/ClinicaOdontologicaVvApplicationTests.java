package com.example.ClinicaOdontologicaVV;

import com.example.ClinicaOdontologicaVV.entity.Odontologo;

import com.example.ClinicaOdontologicaVV.repository.impl.OdontologoDaoH2;
import com.example.ClinicaOdontologicaVV.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClinicaOdontologicaVvApplicationTests {

	private OdontologoService odontologoService;



	@Test
	void RegistrarOdontologoH2(){
		odontologoService = new OdontologoService(new OdontologoDaoH2());
		Odontologo odontologo = new Odontologo(789,"Victor","Valle");

		Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);

		assertNotNull(odontologoRegistrado, "El odontólogo registrado no debería ser nulo");
	}

	@Test
	void listarTodosLosOdontologosRegistradosEnH2(){
		odontologoService = new OdontologoService(new OdontologoDaoH2());
		assertFalse(odontologoService.listarOdontologo().isEmpty());
	}


}
