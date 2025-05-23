package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/convenios")
@RequiredArgsConstructor
public class ConvenioController {

	private final ConvenioMapper mapper;
	private final ConvenioService service;
	
	@PostMapping(value = "")
	public ResponseEntity<ConvenioDto>  generar(@PathVariable("empresaId") Integer empresaId,  @RequestBody @Valid ConvenioAltaDto convenio) {
		
		convenio.setEmpresaId(empresaId);
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
				
		Convenio convenioNew = service.generar(convenio);
		
		return ResponseEntity.ok( mapper.run(convenioNew) );
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ConvenioDto>  get(@PathVariable("empresaId") Integer empresaId, @PathVariable("id") Integer convenioId) {
		log.debug( "ConvenioController.get - convenioId:  " + convenioId.toString() );  
		
		Convenio convenio = service.get(empresaId, convenioId);
		
		return ResponseEntity.ok( mapper.run(convenio) );
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<ConvenioConsultaDto>>  get(@PathVariable("empresaId") Integer empresaId, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate desde, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate hasta		
			) {
		List<ConvenioConsultaDto> lst = null; 
		
		
		return ResponseEntity.ok( lst );
	}
}
