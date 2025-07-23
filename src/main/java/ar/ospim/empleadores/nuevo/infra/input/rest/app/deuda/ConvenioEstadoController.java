package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCambioEstadoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/convenios")
@RequiredArgsConstructor
public class ConvenioEstadoController {
 
	private final ConvenioService service; 
	
	 
	@PostMapping(value = "/{convenioId}/estado-set/{estado}")
	public ResponseEntity<ConvenioCambioEstadoDto>  actualizarEstado(@PathVariable("convenioId") Integer convenioId, @PathVariable("estado") String estado) {
		ConvenioCambioEstadoDto rta = null;
		
		service.cambiarEstado(convenioId, estado);
		
		return ResponseEntity.ok( rta );
	}
	
	 
}
