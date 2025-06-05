package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCambioEstadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioDeudaMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.AfipInteresStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/convenios")
@RequiredArgsConstructor
public class ConvenioController {

	private final ConvenioMapper mapper;
	private final ConvenioDeudaMapper convenioDeudaMapper; 
	private final ConvenioService service;
	private final AfipInteresStorage afipInteresStorage;
	
	
	@PostMapping(value = "")
	public ResponseEntity<ConvenioDto>  generar(@PathVariable("empresaId") Integer empresaId,  @RequestBody @Valid ConvenioAltaDto convenio) {
		
		convenio.setEmpresaId(empresaId);
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
				
		Convenio convenioNew = service.generar(convenio);
		
		return ResponseEntity.ok( mapper.run(convenioNew) );
	}
	
	@PutMapping(value = "/{convenioId}")
	public ResponseEntity<ConvenioDto>  actualizar(@PathVariable("empresaId") Integer empresaId,  @PathVariable("convenioId") Integer convenioId, @RequestBody @Valid ConvenioModiDto convenio) {
		
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
		convenio.setConvenioId(convenioId);
		convenio.setEmpresaId(empresaId);
		Convenio convenioNew = service.actualizar(convenio);
		
		return ResponseEntity.ok( mapper.run(convenioNew) );
	}
	
	@GetMapping(value = "/{id}/deudaDto/all")
	public ResponseEntity<ConvenioDeudaDto>  getAllDeudaDto(@PathVariable("empresaId") Integer empresaId, @PathVariable("id") Integer convenioId) {
		log.debug( "ConvenioController.getDeudaDto - convenioId:  " + convenioId.toString() );  
		
		Convenio convenio = service.get(empresaId, convenioId);
		
		ConvenioDeudaDto rta = mapper.run3(convenio);
		rta.setDeclaracionesJuradas( convenioDeudaMapper.run(convenio.getDdjjs() ) );
		
		return ResponseEntity.ok( rta );
	}
	

	@PostMapping("/calcular-cuota")
	public ResponseEntity<CalcularCuotaDto> getCuota(@PathVariable("empresaId") Integer empresaId, @RequestBody @Valid CalcularCuotaDto dto) {
		
		dto.setImporteCuota(BigDecimal.ZERO);
		dto.setImporteInteresTotal(BigDecimal.ZERO);
		log.debug( "calcular-cuota - dto: " + dto.toString() );
				
		BigDecimal aux = service.calcularImporteCuota(dto.getImporteDeuda(), dto.getCantidadCuota(), dto.getFechaIntencionPago() );
		
		dto.setImporteCuota(aux);
		if ( !BigDecimal.ZERO.equals(aux) )  
			dto.setImporteInteresTotal(aux.multiply( BigDecimal.valueOf( dto.getCantidadCuota() ) ).subtract(dto.getImporteDeuda()));
		
		return ResponseEntity.ok( dto );		 
	}
	

	@PostMapping(value = "/{convenioId}/estado-set/{estado}")
	public ResponseEntity<ConvenioCambioEstadoDto>  actualizarEstado(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("estado") String estado) {
		ConvenioCambioEstadoDto rta = null;
		
		service.cambiarEstado(empresaId, convenioId, estado);
		
		return ResponseEntity.ok( rta );
	}
	
	
 	
	@GetMapping("/prueba")
	public ResponseEntity<BigDecimal> prueba(@PathVariable("empresaId") Integer empresaId) {
		BigDecimal capital = BigDecimal.valueOf(15700);
		log.debug( LocalDate.now().plusDays(15).toString() );
		BigDecimal aux = afipInteresStorage.calcularInteres(capital, LocalDate.now(), LocalDate.now().plusDays(15) );
		return ResponseEntity.ok( aux );
	}

}
