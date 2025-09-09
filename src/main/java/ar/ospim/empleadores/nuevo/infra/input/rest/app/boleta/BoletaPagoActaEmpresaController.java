package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoActaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa/{empresaId}/boletas/pago-acta")
public class BoletaPagoActaEmpresaController {

	private final BoletaPagoDtoMapper mapper;
	private final BoletaPagoActaService service; 
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto> generar(@PathVariable Integer empresaId, @RequestBody BoletaPagoDto dato, HttpServletRequest request) {
		log.debug("dato: {}", dato);
		
		BoletaPagoBO registro = mapper.map(dato, empresaId);
		registro = service.guardar(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());

		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
		
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer empresaId, @PathVariable Integer id, @RequestBody @Valid BoletaPagoDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		
		BoletaPagoBO registro = mapper.map(dato, empresaId, id);
		registro = service.guardar(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void>  borrar( @PathVariable Integer empresaId, @PathVariable Integer id) {
		service.borrar(empresaId, id);
		return ResponseEntity.noContent().<Void>build(); 
	}


}
