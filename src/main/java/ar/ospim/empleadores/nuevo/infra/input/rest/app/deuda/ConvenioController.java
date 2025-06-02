package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.out.store.AfipInteresStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
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
	
	@GetMapping("/prueba")
	public ResponseEntity<BigDecimal> prueba(@PathVariable("empresaId") Integer empresaId) {
		BigDecimal capital = BigDecimal.valueOf(15700);
		
		
		log.debug( LocalDate.now().plusDays(15).toString() );
		
		BigDecimal aux = afipInteresStorage.calcularInteres(capital, LocalDate.now(), LocalDate.now().plusDays(15) );
		
		return ResponseEntity.ok( aux );
	}
	

	@PostMapping("/calcular-cuota")
	public ResponseEntity<CalcularCuotaDto> getCuota(@PathVariable("empresaId") Integer empresaId, @RequestBody @Valid CalcularCuotaDto dto) {
		
		dto.setImporteCuota(BigDecimal.ZERO);
		dto.setImporteInteresTotal(BigDecimal.ZERO);
		log.debug( "calcular-cuota - dto: " + dto.toString() );
				
		BigDecimal aux = service.calcularImporteCuota(dto.getImporteDeuda(), dto.getCantidadCuota(), dto.getFechaIntencionPago() );
		
		dto.setImporteCuota(aux);
		if ( !aux.ZERO.equals(aux) )  
			dto.setImporteInteresTotal(aux.multiply( BigDecimal.valueOf( dto.getCantidadCuota() ) ).subtract(dto.getImporteDeuda()));
		
		return ResponseEntity.ok( dto );		 
	}
	
	
	@PostMapping(value = "")
	public ResponseEntity<ConvenioDto>  generar(@PathVariable("empresaId") Integer empresaId,  @RequestBody @Valid ConvenioAltaDto convenio) {
		
		convenio.setEmpresaId(empresaId);
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
				
		Convenio convenioNew = service.generar(convenio);
		
		return ResponseEntity.ok( mapper.run(convenioNew) );
	}
	
	@PostMapping(value = "/{convenioId}/estado-set/{estado}")
	public ResponseEntity<ConvenioCambioEstadoDto>  actualizarEstado(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("estado") String estado) {
		ConvenioCambioEstadoDto rta = null;
		
		service.cambiarEstado(empresaId, convenioId, estado);
		
		return ResponseEntity.ok( rta );
	}
	
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
		
		ConvenioDeudaDto rta = mapper.run3(convenio);
		rta.setDeclaracionesJuradas( convenioDeudaMapper.run(convenio.getDdjjs() ) );
		
		return ResponseEntity.ok( rta );
	}
	
	
	@GetMapping(value = "")
	public ResponseEntity<List<ConvenioConsultaDto>>  get(@PathVariable("empresaId") Integer empresaId,
			@Nullable @RequestParam String estado, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate desde, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate hasta		
			) {
		
		List<ConvenioConsultaDto> lst = null; 
		
		ConvenioConsultaFiltro filtro = new ConvenioConsultaFiltro();
		filtro.setDesde(desde);
		filtro.setHasta(hasta);
		filtro.setEmpresaId(empresaId);
		filtro.setEstado(estado);
		
		List<Convenio> lstAux = service.get(filtro);
		lst = mapper.run4( lstAux );
		
		return ResponseEntity.ok( lst );
	}

	@GetMapping(value = "/{id}/cuotas")
	public ResponseEntity<List<ConvenioCuotaConsultaDto>> getCuotas(@PathVariable("empresaId") Integer empresaId, @PathVariable("id") Integer convenioId) {
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
		
		service.actualizar(dto);		
		
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
	
	
	@DeleteMapping(value = "/{convenioId}/actas/{actaId}")
	public ResponseEntity<Void> borrarActa(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("actaId") Integer actaId) {
		
		service.borrarActa(empresaId, convenioId, actaId);		
		
		return ResponseEntity.ok( null );
	}
	
	@PostMapping(value = "/{convenioId}/actas/{actaId}")
	public ResponseEntity<Void> altaActa(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("actaId") Integer actaId) {
		
		service.asignarActa(empresaId, convenioId, actaId);		
		
		return ResponseEntity.ok( null );
	}
	
	@DeleteMapping(value = "/{convenioId}/ajustes/{ajusteId}")
	public ResponseEntity<Void> borrarAjuste(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("ajusteId") Integer ajusteId) {
		
		service.borrarAjuste(empresaId, convenioId, ajusteId);		
		
		return ResponseEntity.ok( null );
	}
	
	@PostMapping(value = "/{convenioId}/ajustes/{ajusteId}")
	public ResponseEntity<Void> altaAjuste(@PathVariable("empresaId") Integer empresaId, @PathVariable("convenioId") Integer convenioId, @PathVariable("ajusteId") Integer ajusteId) {
		
		service.asignarAjuste(empresaId, convenioId, ajusteId);		
		
		return ResponseEntity.ok( null );
	}
}
