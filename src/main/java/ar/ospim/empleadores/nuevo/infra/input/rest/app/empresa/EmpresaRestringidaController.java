package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaRestringidaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaRestringidaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.EmpresaRestringidaDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("empresa/restringida")
@RequiredArgsConstructor
public class EmpresaRestringidaController {
	
	private final EmpresaRestringidaService service;
	private final EmpresaRestringidaDtoMapper mapper;
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid EmpresaRestringidaDto dato, HttpServletRequest request) {
		
		EmpresaRestringidaBO registro = mapper.map(dato);
		registro = service.crear(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?>  actualizar(@PathVariable Integer id, @RequestBody @Valid EmpresaRestringidaDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		
		EmpresaRestringidaBO registro = mapper.map(id, dato);
		registro = service.crear(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent( ).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public void  borrar( @PathVariable Integer id ) {
		service.borrar(id);
	}
	
	@GetMapping
	public ResponseEntity<List<EmpresaRestringidaDto>>  consultar() {
		List<EmpresaRestringidaBO> consulta = service.consultar();
		List<EmpresaRestringidaDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}

}
