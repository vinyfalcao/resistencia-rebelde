package br.com.starwars.resistenciarebelde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ResistenciaRebeldeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResistenciaRebeldeApplication.class, args);
	}

}
