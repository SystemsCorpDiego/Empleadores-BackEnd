package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJActualizarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJAltaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJBajaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJImprimirService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJPresentarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarNomina;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarPresentacion;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPeriodoAnteriorDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPresentarResponseDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesEmpresaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarCuilDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarErrorDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.mapper.DDJJDtoAltaMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.mapper.DDJJDtoMapper;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/empresa/{empresaId}/ddjj")
@RequiredArgsConstructor
public class DDJJEmpresaController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final DDJJDtoMapper mapper;
	private final DDJJDtoAltaMapper mapperAlta;
	private final DDJJAltaService altaService;
	private final DDJJActualizarService actualizarService;
	private final DDJJConsultarService consultarService; 
	private final DDJJBajaService bajaService;
	private final DDJJPresentarService presentarService;
	private final DDJJImprimirService imprimirService; 
	private final DDJJValidarPresentacion validarPresentacionService;
	private final DDJJValidarNomina validarNominaService;
	
	@GetMapping(value = "/totales")
	public ResponseEntity<List<DDJJTotalesEmpresaDto>>  consultarDDJJTotales(@PathVariable Integer empresaId, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate desde, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate hasta) {
		logger.debug( "empresaId: " + empresaId.toString());		 
		
		DDJJConsultaFiltroDto filtro = new DDJJConsultaFiltroDto();
		if ( empresaId != null ) {
			filtro.setEmpresaId(empresaId);
		}
		if ( desde != null ) {
			filtro.setDesde(desde);
		}
		if ( hasta != null ) {
			filtro.setHasta(hasta);
		}
		
		List<DDJJTotalesEmpresaDto> lst = consultarService.consultarTotales(filtro);
		
		logger.debug("FIN" );
		return ResponseEntity.ok( lst );	 
	}
	
	@GetMapping(value = "/periodo-anterior")
	public ResponseEntity<DDJJPeriodoAnteriorDto>  consultarPeriodoAnterior(@PathVariable Integer empresaId, 
			@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable LocalDate periodo) {
		logger.debug( "empresaId: " + empresaId.toString());		
		LocalDate periodoAux = null;
		if ( periodo != null)
			periodoAux = periodo;
		
		DDJJBO ddjj = consultarService.consultarPeriodoAnterior(empresaId, periodoAux);
		
		logger.debug("FIN" );
		return ResponseEntity.ok( mapper.mapPeriodoAnterior(ddjj) );	 
	}
		
	@GetMapping(value = "/{id}")
	public ResponseEntity<DDJJConsultaDto>  consultar(@PathVariable Integer id) {
		logger.debug( "ddjjId: " + id.toString());		
		
		DDJJBO ddjj= consultarService.consultar(id);
		
		logger.debug("FIN" );
		return ResponseEntity.ok( mapper.mapConsulta(ddjj) );	 
	}
	
	@GetMapping(value = {"/presentada/periodio/{periodo}", "/presentada/periodio" })
	public ResponseEntity<DDJJConsultaDto>  consultarPeriodoPresentada (	
			@PathVariable Integer empresaId,
			@PathVariable(required = false) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			LocalDate periodo
			) {
		if ( periodo != null )		
			logger.debug( "periodo: " + periodo.toString());		
		
		Optional<DDJJBO> cons = consultarService.getUltimaPresentada(empresaId, periodo);
		
		if ( cons.isPresent() ) 
			return ResponseEntity.ok( mapper.mapConsulta(cons.get()) );
		
		logger.debug("FIN" );
		return ResponseEntity.noContent().build();	 
	}
	
	@PostMapping
	public ResponseEntity<DDJJAltaDto>  alta(@PathVariable Integer empresaId, @RequestBody @Valid DDJJAltaDto ddjj, HttpServletRequest request) {
		logger.debug("guardar() - Params => empresaId: " + empresaId + " - ddjj: " + ddjj.toString() );
		
		DDJJBO registro = mapperAlta.mapAlta(empresaId, ddjj);		
		for ( DDJJEmpleadoBO reg: registro.getEmpleados() ) {
			reg.getAfiliado().setInte(0);
		}
		
		registro = altaService.run(registro);
		DDJJAltaDto rta = mapper.map(registro); 
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", rta.getId()) );
		
		logger.debug("empresaId: " + rta.toString());
		return ResponseEntity.created( location ).body(rta); 	 
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<DDJJAltaDto>  actualizar(@PathVariable Integer empresaId, @PathVariable Integer id,  @RequestBody @Valid DDJJAltaDto ddjj) {
		logger.debug("empresaId: " + empresaId + "id: " + id + " - ddjj: " + ddjj.toString());

		ddjj.setId(id);		
		DDJJBO registro = mapper.map(empresaId, ddjj);
		for ( DDJJEmpleadoBO reg: registro.getEmpleados() ) {
			reg.getAfiliado().setInte(0);
		}
				
		registro = actualizarService.run(registro);
		DDJJAltaDto rta = mapper.map(registro); 
		
		logger.debug("FIN" );
		return ResponseEntity.ok(rta); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void>  baja(@PathVariable Integer empresaId, @PathVariable Integer id) {
		logger.debug("empresaId: " + empresaId + "id: " + id );
		
		bajaService.run(id);
		
		logger.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@GetMapping(value = "/{id}/imprimir")
	public ResponseEntity<?> imprimir(@PathVariable Integer empresaId, @PathVariable Integer id)   throws JRException, SQLException {
		logger.debug("empresaId: " + empresaId + "id: " + id );
		 
		logger.debug("FIN" );
		byte[] auxPdf = imprimirService.run(id);
		
		String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + "ddjj_1.pdf" + "\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(auxPdf);  
        
		//return ResponseEntity.noContent().<Void>build();
		//return ResponseEntity.ok(aux);
	}
	
	@PostMapping(value = "/validar")
	public ResponseEntity<DDJJValidarDto>  validar(@PathVariable Integer empresaId, @RequestBody @Valid DDJJAltaDto ddjj) {
		logger.debug("empresaId: " + empresaId + "ddjj: " + ddjj.toString() );
		DDJJValidarDto rtaFinal = new DDJJValidarDto() ;

		DDJJBO ddjjBo = mapper.map(ddjj);
		Optional<List<DDJJValidarErrorDto>> lst = validarPresentacionService.run(ddjjBo);
		if( lst.isPresent()) { 
			rtaFinal.setErrores(lst.get());
		}
		
		logger.debug("FIN" );
		return ResponseEntity.ok(rtaFinal); 
	}
	
	@PostMapping(value = "/upload/nomina/validaCuil")
	public ResponseEntity<List<DDJJValidarCuilDto>>  validarNominaCuil(@PathVariable Integer empresaId, @RequestBody  List<String> lstCuil ) {
		logger.debug("empresaId: " + empresaId + "lstCuil: " + lstCuil.toString() );
		
		List<DDJJValidarCuilDto> lst = mapper.map(lstCuil);
		lst = validarNominaService.run(lst);
		
		logger.debug("FIN" );
		return ResponseEntity.ok( lst );
	}
	
	@PatchMapping(value = "/{id}/presentar")
	public ResponseEntity<DDJJPresentarResponseDto>  presentar(@PathVariable Integer empresaId, @PathVariable Integer id) {
		logger.debug("empresaId: " + empresaId + "id: " + id );
		
		DDJJPresentarResponseDto rta = presentarService.run(id);
		
		logger.debug("FIN" );
		return ResponseEntity.ok(rta); 
		//return ResponseEntity.noContent().<Void>build(); 
	}
	
}
