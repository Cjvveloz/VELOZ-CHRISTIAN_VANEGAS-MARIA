package com.example.ClinicaOdontologicaVV;

import com.example.ClinicaOdontologicaVV.dbconnection.H2Connection;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClinicaOdontologicaVvApplication {



	public static void main(String[] args) {

		H2Connection.ejecutarPrimerScript();

		SpringApplication.run(ClinicaOdontologicaVvApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
