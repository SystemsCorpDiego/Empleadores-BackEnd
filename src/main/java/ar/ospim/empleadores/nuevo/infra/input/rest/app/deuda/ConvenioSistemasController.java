package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/convenios")
@RequiredArgsConstructor
public class ConvenioSistemasController {

	private final ConvenioMapper mapper;
	private final ConvenioService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ConvenioDto>  get(@PathVariable("empresaId") Integer empresaId, @PathVariable("id") Integer convenioId) {
		log.debug( "ConvenioController.get - convenioId:  " + convenioId.toString() );  
		
		Convenio convenio = service.get(empresaId, convenioId);
		ConvenioDto rta = mapper.run(convenio);
		
		return ResponseEntity.ok( rta );
	}

	@GetMapping(value = "/{id}/deudaDto")
	public ResponseEntity<ConvenioDeudaDto>  getDeudaDto(@PathVariable("empresaId") Integer empresaId, @PathVariable("id") Integer convenioId) {
		log.debug( "ConvenioController.getDeudaDto - convenioId:  " + convenioId.toString() );  
		
		Convenio convenio = service.get(empresaId, convenioId);		
		ConvenioDeudaDto rta = service.getConvenioDeudaDto(convenio);
		
		return ResponseEntity.ok( rta );
	}
	
	@PostMapping(value = "/{convenioId}/plan-pago")
	public ResponseEntity<Void>  actualizarPlanPago(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId,  @RequestBody @Valid PlanPagoDto planPago) {				
		log.debug( "ConvenioController.actualizarPlanPago - planPago " + planPago.toString() );  
				
		//Convenio convenioNew = service.generar(convenio);
		service.actualizarPlanPago(empresaId, convenioId, planPago);
		
		return ResponseEntity.ok( null );
	}

	@PostMapping(value = "/{convenioId}/actas/{actaId}")
	public ResponseEntity<Void> altaActa(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("actaId") Integer actaId) {
		
		service.asignarActa(empresaId, convenioId, actaId);		
		
		return ResponseEntity.ok( null );
	}
	
	@DeleteMapping(value = "/{convenioId}/actas/{actaId}")
	public ResponseEntity<Void> borrarActa(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("actaId") Integer actaId) {
		
		service.borrarActa(empresaId, convenioId, actaId);		
		
		return ResponseEntity.ok( null );
	}

	@PostMapping(value = "/{convenioId}/ajustes/{ajusteId}")
	public ResponseEntity<Void> altaAjuste(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("ajusteId") Integer ajusteId) {
		
		service.asignarAjuste(empresaId, convenioId, ajusteId);		
		
		return ResponseEntity.ok( null );
	}
	
	@DeleteMapping(value = "/{convenioId}/ajustes/{ajusteId}")
	public ResponseEntity<Void> borrarAjuste(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("ajusteId") Integer ajusteId) {
		
		service.borrarAjuste(empresaId, convenioId, ajusteId);		
		
		return ResponseEntity.ok( null );
	}

	@PostMapping(value = "/{convenioId}/ddjj/{ddjjId}")
	public ResponseEntity<Void> ddjjAlta(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("ddjjId") Integer ddjjId) {
		
		service.asignarDDJJ(empresaId, convenioId, ddjjId);		
		
		return ResponseEntity.ok( null );
	}
	
	@DeleteMapping(value = "/{convenioId}/ddjj/{ddjjId}")
	public ResponseEntity<Void> ddjjBorrar(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("ddjjId") Integer ddjjId) {
		
		service.borrarDDJJ(empresaId, convenioId, ddjjId);		
		
		return ResponseEntity.ok( null );
	}
	
	
}
