package ar.ospim.empleadores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EmpleadoresApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoresApplication.class, args);
	}
 
	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
		return application.sources(EmpleadoresApplication.class);  
	}  
	
}
