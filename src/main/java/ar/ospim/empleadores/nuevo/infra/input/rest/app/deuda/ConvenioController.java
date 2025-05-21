package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/convenio")
@RequiredArgsConstructor
public class ConvenioController {

	private final ConvenioMapper mapper;
	private final ConvenioService service;
	
	@PostMapping(value = "")
	public ResponseEntity<ConvenioAltaResponseDto>  generar(@PathVariable("empresaId") Integer empresaId,  @RequestBody @Valid ConvenioAltaDto convenio) {
		
		convenio.setEmpresaId(empresaId);
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
				
		Convenio convenioNew = service.generar(convenio);
		
		
		return ResponseEntity.ok( mapper.run(convenioNew) );
	}
}
