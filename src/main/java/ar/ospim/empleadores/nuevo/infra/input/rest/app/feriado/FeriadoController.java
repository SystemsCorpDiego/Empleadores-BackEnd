package ar.ospim.empleadores.nuevo.infra.input.rest.app.feriado;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.feriado.FeriadoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.feriado.dto.FeriadoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("feriados")
@RequiredArgsConstructor
@CrossOrigin(origins="http://127.0.0.1:5173/")
public class FeriadoController {
	
	private final FeriadoService service;
	private final FeriadoDtoMapper mapper;

	
	@GetMapping(value = {"", "/{anio}"})
	public ResponseEntity<List<FeriadoDto>>  consultar(@PathVariable(required = false) Integer anio) {
		List<FeriadoBO> consulta;
		if ( anio == null ) {
			consulta = service.consultar();
		} else {
			consulta = service.consultarAnio(anio);
		}
		List<FeriadoDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}
	
	@PostMapping()
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid FeriadoDto dato, HttpServletRequest request) {
		
		FeriadoBO registro = mapper.map(dato);
		registro = service.guardar(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer id, @RequestBody @Valid FeriadoDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		
		FeriadoBO registro = mapper.map(id, dato);
		registro = service.guardar(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void>  borrar( @PathVariable Integer id ) {
		service.borrar(id);
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@PostMapping("/duplicar/{anio}")
	public ResponseEntity<IdGeneradoDto>  agregar( @PathVariable Integer anio) {		
		service.duplicarAnio(anio);				
		return ResponseEntity.created( null ).body(null);
	}
	
}
