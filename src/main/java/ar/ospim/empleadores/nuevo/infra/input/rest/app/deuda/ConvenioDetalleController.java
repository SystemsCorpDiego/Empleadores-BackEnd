package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;

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

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ConvenioChequeEstadoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/convenios")
@RequiredArgsConstructor
public class ConvenioDetalleController {

	private final ConvenioMapper mapper;
	private final ConvenioService service;	

	@GetMapping(value = "/{convenioId}/cuotas")
	public ResponseEntity<List<ConvenioCuotaConsultaDto>> getCuotas(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId) {
		List<ConvenioCuotaConsultaDto> rta = null;
		rta = service.getCuotas(empresaId, convenioId);
		return ResponseEntity.ok( rta );
	}

	@GetMapping(value = "/{convenioId}/cuotas/{cuotaId}/cheques")
	public ResponseEntity<List<ConvenioCuotaChequeConsultaDto>> getCuotaCheques(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("cuotaId") Integer cuotaId) {
		List<ConvenioCuotaChequeConsultaDto>rta = null;
		List<ConvenioCuotaCheque>  lst = service.getCheques(empresaId, convenioId, cuotaId);
		rta = mapper.run7(lst);
		return ResponseEntity.ok( rta );
	}
	
	
	@GetMapping(value = "/{convenioId}/cuotas/{cuotaId}/cheques/{chequeId}")
	public ResponseEntity<ConvenioCuotaChequeConsultaDto> getCuotaCheques(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("cuotaId") Integer cuotaId, @PathVariable("chequeId") Integer chequeId) {
		ConvenioCuotaChequeConsultaDto rta = null;
		ConvenioCuotaCheque reg = service.getCheque(empresaId, convenioId, cuotaId, chequeId);
		rta = mapper.run2(reg);
		return ResponseEntity.ok( rta );
	}
	

	@PutMapping(value = "/{convenioId}/cuotas/{cuotaId}/cheques/{chequeId}")
	public ResponseEntity<Void> actualizarCuotaCheques(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("cuotaId") Integer cuotaId, @PathVariable("chequeId") Integer chequeId, @RequestBody @Valid ConvenioCuotaChequeAltaDto dto) {
		
		dto.setConvenioId(convenioId);
		dto.setEmpresaId(empresaId);
		dto.setCuotaId(cuotaId);
		dto.setChequeId(chequeId);
		
		service.actualizarCuotaCheque(dto);		
		
		return ResponseEntity.ok( null );
	}
	
	@DeleteMapping(value = "/{convenioId}/cuotas/{cuotaId}/cheques/{chequeId}")
	public ResponseEntity<Void> borrarCuotaCheques(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("cuotaId") Integer cuotaId, @PathVariable("chequeId") Integer chequeId) {
		
		service.borrarCheque(empresaId, convenioId, cuotaId, chequeId);		
		
		return ResponseEntity.ok( null );
	}
	
	@PostMapping(value = "/{convenioId}/cuotas/{cuotaId}/cheques")
	public ResponseEntity<ConvenioCuotaChequeDto>  generarCuota(@PathVariable("empresaId") Integer empresaId,  @PathVariable("convenioId") Integer convenioId,  @PathVariable("cuotaId") Integer cuotaId, @RequestBody @Valid ConvenioCuotaChequeAltaDto cheque) {
		
		cheque.setEmpresaId(empresaId);
		cheque.setConvenioId(convenioId);
		cheque.setCuotaId(cuotaId);
		
		log.debug( "ConvenioController.generar - cheque " + cheque.toString() );  
				
		ConvenioCuotaCheque chequeNew = service.generar(cheque);
		
		return ResponseEntity.ok( mapper.run(chequeNew) );
	}
	
	
}
