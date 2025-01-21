package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ar.ospim.empleadores.nuevo.app.dominio.DomicilioTipoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaDomicilioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.CodigoDescripDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaDomicilioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaDomicilioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaPlantaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.DomicilioTipoDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.EmpresaDomicilioDtoMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping( "empresa")
@RequiredArgsConstructor
public class EmpresaDomicilioController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final EmpresaDomicilioService service;
	private final EmpresaDomicilioDtoMapper mapper;
	private final DomicilioTipoDtoMapper domicilioTipoMapper;
	

	@GetMapping(value = "/{empresaId}/domicilio")
	public ResponseEntity<List<EmpresaDomicilioDto>>  consultar(@PathVariable("empresaId") Integer empresaId) {
		
		List<EmpresaDomicilioBO> consulta = service.consultar(empresaId); 

		List<EmpresaDomicilioDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}	
	
	@GetMapping(value = "/{empresaId}/domicilio/planta")
	public ResponseEntity<List<EmpresaPlantaDto>>  consultarPlanta(@PathVariable("empresaId") Integer empresaId) {
		
		List<EmpresaDomicilioBO> consulta = service.consultarTipoReales(empresaId); 

		List<EmpresaPlantaDto> consultaDto = mapper.mapPlanta(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}	
	
	@GetMapping( value={"/domicilio/tipo", "/public/domicilio/tipo"} )
	public ResponseEntity<List<CodigoDescripDto>>  consultar() {
		
		List<DomicilioTipoBO> consulta = service.consultarTipos(); 
 
		List<CodigoDescripDto> consultaDto = domicilioTipoMapper.map(consulta);
		return ResponseEntity.ok( consultaDto );
	}	

	
	@PostMapping(value = "{empresaId}/domicilio")
	public ResponseEntity<IdGeneradoDto>  agregar(@PathVariable("empresaId") @NotNull Integer empresaId, @RequestBody @Valid EmpresaDomicilioAltaDto dto, HttpServletRequest request) {
		
		EmpresaDomicilioBO registro = mapper.map(dto) ;
		registro = service.guardar(empresaId, registro);
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());

		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
		return ResponseEntity.created( location ).body(rta);
	}

	@PutMapping(value = "/{empresaId}/domicilio/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable("empresaId") @NotNull Integer empresaId, @PathVariable("id") @NotNull Integer id, @RequestBody @Valid EmpresaDomicilioAltaDto dato) {
		logger.debug("id: " + id + " - dato: " + dato.toString());
		  
		EmpresaDomicilioBO registro = mapper.map(id, dato);
		registro = service.guardar(empresaId, registro);
		
		logger.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}

	
	@DeleteMapping(value = "/{empresaId}/domicilio/{id}")
	public ResponseEntity<Void> borrar( @PathVariable("empresaId") @NotNull Integer empresaId, @PathVariable("id") @NotNull Integer id ) {
		service.borrar(empresaId, id);
		return ResponseEntity.noContent().<Void>build(); 
	}
	
}
