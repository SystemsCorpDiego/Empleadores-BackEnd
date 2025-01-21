package ar.ospim.empleadores.nuevo.infra.input.rest.app.formapago;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/formas-pago")
@RequiredArgsConstructor
public class FormaPagoController {
	
	private final FormaPagoService service;
	private final FormaPagoDtoMapper mapper;
	
	@GetMapping()
	public ResponseEntity<List<FormaPagoDto>>  consultar() {
		List<FormaPagoBO> consulta = service.consultar();
		List<FormaPagoDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}

}
