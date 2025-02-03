package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoTipoBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.CodigoDescripDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaContactoAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaContactoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.ContactoTipoDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.EmpresaContactoDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("empresa")
@RequiredArgsConstructor
public class EmpresaContactoController {
 
	private final EmpresaContactoService service;
	private final EmpresaContactoDtoMapper mapper;
	private final ContactoTipoDtoMapper contactoTipoMapper;


	@GetMapping(value = "/{empresaId}/contacto")
	public ResponseEntity<List<EmpresaContactoDto>>  consultar(@PathVariable("empresaId") Integer empresaId) {
		
		List<ContactoBO> consulta = service.consultar(empresaId); 

		List<EmpresaContactoDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}	
	
	@GetMapping(value = "/contacto/tipo")
	public ResponseEntity<List<CodigoDescripDto>>  consultar() {
		
		List<ContactoTipoBO> consulta = service.consultarTipos(); 

		List<CodigoDescripDto> consultaDto = contactoTipoMapper.map(consulta);
		return ResponseEntity.ok( consultaDto );
	}	
	
	@PostMapping(value = "{empresaId}/contacto")
	public ResponseEntity<IdGeneradoDto>  agregar(@PathVariable("empresaId") @NotNull Integer empresaId, @RequestBody @Valid EmpresaContactoAltaDto dto, HttpServletRequest request) {
		
		ContactoBO registro = mapper.map(dto) ;
		registro = service.guardar(empresaId, registro);
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());

		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
		return ResponseEntity.created( location ).body(rta);
	}

	@PutMapping(value = "/{empresaId}/contacto/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable("empresaId") @NotNull Integer empresaId, @PathVariable("id") @NotNull Integer id, @RequestBody @Valid EmpresaContactoDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		  
		ContactoBO registro = mapper.map(id, dato);
		registro = service.guardar(empresaId, registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}

	
	@DeleteMapping(value = "/{empresaId}/contacto/{id}")
	public ResponseEntity<Void> borrar( @PathVariable("empresaId") @NotNull Integer empresaId, @PathVariable("id") @NotNull Integer id ) {
		service.borrar(empresaId, id);
		return ResponseEntity.noContent().<Void>build(); 
	}
	
}
