package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.EmpresaModiDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.EmpresaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("empresa")
@RequiredArgsConstructor
public class EmpresaController {
	private final EmpresaModiDtoMapper mapper;
	private final EmpresaService service;
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer id, @RequestBody @Valid EmpresaModiDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		
		EmpresaBO registro = mapper.map(id, dato);
		registro = service.updateEmpresa(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}

	
	@GetMapping
	public ResponseEntity<List<EmpresaDto>>  consultar( @RequestParam(required = false) String cuit ) {
		List<EmpresaBO> cons = null;
		if ( cuit != null ) {
			EmpresaBO reg = service.getEmpresa(cuit);
			cons = new ArrayList<EmpresaBO>();
			cons.add(reg);
		} else {
			cons = service.findAll();
		}
		List<EmpresaDto> rta = mapper.mapCons(cons);
		return ResponseEntity.ok( rta );
	}
 
}
 