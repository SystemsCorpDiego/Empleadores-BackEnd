package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.EntidadBO;
import ar.ospim.empleadores.nuevo.app.servicios.entidad.EntidadService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("entidades")
@RequiredArgsConstructor
public class EntidadController {
	
	private final EntidadService service;
	
	@GetMapping 
	public ResponseEntity<List<EntidadBO>>  consultar() {
		List<EntidadBO>  resultado = service.consultar();		 
		return ResponseEntity.ok( resultado );
	}	 
}
