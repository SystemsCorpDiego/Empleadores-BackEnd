package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioSeteoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioSeteoCuitDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioSeteoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioSeteoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/convenio-seteo")
@RequiredArgsConstructor
public class ConvenioSeteoController {

	private final MessageSource messageSource;
	private final ConvenioSeteoMapper mapper;
	private final ConvenioSeteoService service;
	
	@PostMapping(value = "")
	public ResponseEntity<ConvenioSeteoDto>  generar(@RequestBody @Valid ConvenioSeteoDto convenioSeteo) {		
		ConvenioSeteo reg = mapper.run(convenioSeteo);
		reg.setId(null);		
		reg = service.guardar(reg);
		ConvenioSeteoDto dto = mapper.run(reg);
		return ResponseEntity.ok( dto );
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ConvenioSeteoDto>  actualizar(@PathVariable("id") Integer id, @RequestBody @Valid ConvenioSeteoDto convenioSeteo) {		
		ConvenioSeteo reg = mapper.run(convenioSeteo);
		reg.setId(id);
		reg = service.guardar(reg);
		ConvenioSeteoDto dto = mapper.run(reg);
		return ResponseEntity.ok( dto );
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ConvenioSeteoDto>  getDto(@PathVariable("id") Integer id) {
		log.debug( "ConvenioSeteoController.getDto - id:  " + id );  		
		ConvenioSeteo reg = service.get(id);		
		ConvenioSeteoDto dto = mapper.run(reg);		 
		return ResponseEntity.ok( dto );
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<ConvenioSeteoDto>>  getAllDto() {
		log.debug( "ConvenioSeteoController.getAllDto");  		
		List<ConvenioSeteo> lst = service.getAll()	;	
		List<ConvenioSeteoDto> lstDto = mapper.run(lst);		 
		return ResponseEntity.ok( lstDto );
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ConvenioSeteoDto>  delete(@PathVariable("id") Integer id) {
		log.debug( "ConvenioSeteoController.delete - id:  " + id );  		
		service.borrar(id);		
		return ResponseEntity.ok( null);
	}

	@GetMapping(value = "/cuit/{cuit}")
	public ResponseEntity<ConvenioSeteoCuitDto>  getVigentePorCuit(@PathVariable("cuit") String cuit) {
		log.debug( "ConvenioSeteoController.getSeteosCuit - cuit:  " + cuit );  		
		
		if ( cuit == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "CUIT") );
		}
		
		ConvenioSeteo reg = service.getVigentePorCuit(cuit);		
		if ( reg == null)
			return ResponseEntity.noContent().build();
		
		ConvenioSeteoCuitDto dto = mapper.run2(reg);
		return ResponseEntity.ok( dto );
	}
	
}
