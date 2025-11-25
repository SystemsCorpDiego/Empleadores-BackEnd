package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioActualizarService;
import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioCrearService;
import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioImprimirService;
import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCambioEstadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDeudaNominaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioDeudaMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.AfipInteresStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/convenios")
@RequiredArgsConstructor
public class ConvenioController {

	private final ConvenioMapper mapper;
	private final ConvenioDeudaMapper convenioDeudaMapper; 
	private final ConvenioService service;
	private final ConvenioActualizarService actualizarService;
	private final ConvenioCrearService crearService;
	private final ConvenioImprimirService imprimirService;
	private final AfipInteresStorage afipInteresStorage;
	
	
	@PostMapping(value = "")
	public ResponseEntity<ConvenioDto>  generar(@PathVariable("empresaId") Integer empresaId,  @RequestBody @Valid ConvenioAltaDto convenio) {
		
		convenio.setEmpresaId(empresaId);
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
				
		Convenio convenioNew = crearService.run(convenio);
		
		ConvenioDto dto = mapper.run(convenioNew); 		
		dto = castPeriodos(convenioNew, dto);
		
		return ResponseEntity.ok( dto );
	}
	
	@PutMapping(value = "/{convenioId}")
	public ResponseEntity<ConvenioDto>  actualizar(@PathVariable("empresaId") Integer empresaId,  @PathVariable("convenioId") Integer convenioId, @RequestBody @Valid ConvenioModiDto convenio) {
		
		log.debug( "ConvenioController.generar - convenio " + convenio.toString() );  
		convenio.setConvenioId(convenioId);
		convenio.setEmpresaId(empresaId);
		Convenio convenioNew = actualizarService.run(convenio);		

		ConvenioDto dto = mapper.run(convenioNew); 		
		dto = castPeriodos(convenioNew, dto);
		
		return ResponseEntity.ok( dto );
	}
	
	@GetMapping(value = "/{convenioId}/imprimir")
	public ResponseEntity<?> imprimir(@PathVariable Integer empresaId, @PathVariable Integer convenioId)   throws JRException, SQLException {
		log.debug("empresaId: " + empresaId + "id: " + convenioId );
		
		Convenio convenio = service.get(empresaId, convenioId);
		List<ConvenioCuotaConsultaDto>  lstCuotas = service.getCuotas(empresaId, convenioId);
		byte[] auxPdf = imprimirService.run(convenio, lstCuotas);
		
		String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + "convenio.pdf" + "\"";
         

        log.debug("FIN" );
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(auxPdf);           
	}
	
	@GetMapping(value = "/{id}/deudaDto/all")
	public ResponseEntity<ConvenioDeudaDto>  getAllDeudaDto(@PathVariable("empresaId") Integer empresaId, @PathVariable("id") Integer convenioId) {
		log.debug( "ConvenioController.getDeudaDto - convenioId:  " + convenioId.toString() );  
		
		Convenio convenio = service.get(empresaId, convenioId);
		
		ConvenioDeudaDto rta = mapper.run3(convenio);
		
		rta.setDeclaracionesJuradas( convenioDeudaMapper.run2(convenio.getPeriodos() ) );
		
		return ResponseEntity.ok( rta );
	}
	
	
	//CalcularCuotasCalculadaDto
	@PostMapping("/calcular-cuota")
	public ResponseEntity<List<CalcularCuotasCalculadaDto>> getCuotas(@PathVariable("empresaId") Integer empresaId, @RequestBody @Valid CalcularCuotaDto dto) {		
		dto.setImporteCuota(BigDecimal.ZERO);
		dto.setImporteInteresTotal(BigDecimal.ZERO);
		log.debug( "calcular-cuota-new - dto: " + dto.toString() );
				
		List<CalcularCuotasCalculadaDto> lst = service.calcularCuotas(empresaId, dto.getImporteDeuda(), dto.getCantidadCuota(), dto.getFechaIntencionPago() );
				
		return ResponseEntity.ok( lst );		 
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

	private ConvenioDto castPeriodos(Convenio convenio, ConvenioDto dto) {
		
		if ( "OSPIM".equals( convenio.getEntidad() ) ) {
			if ( convenio.getPeriodos() != null ) {
				List<ConvenioDDJJDto> ddjjs = new ArrayList<ConvenioDDJJDto>();
				ConvenioDDJJDto ddjj = null;
				List<ConvenioDDJJDeudaNominaDto> deudaNominas = null;
				ConvenioDDJJDeudaNominaDto deudaNominaDto = null;
				for ( ConvenioPeriodoDetalle reg : convenio.getPeriodos()) {
					ddjj = new ConvenioDDJJDto();
					ddjj.setId( reg.getId() );
					ddjj.setDdjjId( reg.getDeudaNominaId() );
					ddjj.setPeriodo( reg.getPeriodo());
					
					deudaNominas = new ArrayList<ConvenioDDJJDeudaNominaDto>();
					deudaNominaDto = new ConvenioDDJJDeudaNominaDto();
					deudaNominaDto.setAporte( reg.getAporte());
					deudaNominaDto.setAporteDescripcion(null);
					deudaNominaDto.setBoletaId(null);
					deudaNominaDto.setId(reg.getDeudaNominaId() );
					deudaNominaDto.setImporte(reg.getImporte());
					deudaNominaDto.setInteres(reg.getInteres());
					deudaNominas.add(deudaNominaDto);
					ddjj.setDeudaNominas(deudaNominas);
					
					ddjjs.add(ddjj);
				}
				dto.setDdjjs(ddjjs);				
			}
		}
		
		return dto;
	}
	
}
