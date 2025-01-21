package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJConsultaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoConsDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boletas")
@RequiredArgsConstructor
public class BoletaPagoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	private final BoletaPagoConsultaService consultaService;
	private final BoletaPagoDDJJConsultaService boletaPagoConsultaService;
	private final BoletaPagoDtoMapper mapper;
	
	@GetMapping("/consulta-gral")
	public ResponseEntity<BoletaPagoConsDto>  consultar(
			@Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodoDesde, 
			@Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodoHasta,
			@Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pagoDesde, 
			@Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pagoHasta,
			@Nullable @RequestParam String cuit, 
			@Nullable @RequestParam String concepto, 
			@Nullable @RequestParam String entidad, 
			@Nullable @RequestParam String formaPago
			) {
		BoletaPagoFiltroDto filtro = new BoletaPagoFiltroDto();
		filtro.setConcepto(concepto);
		filtro.setCuit(cuit);
		filtro.setEntidad(entidad);
		filtro.setFormaPago(formaPago);
		filtro.setPagoDesde(pagoDesde);
		filtro.setPagoHasta(pagoHasta);
		filtro.setPeriodoDesde(periodoDesde);
		filtro.setPeriodoHasta(periodoHasta);
		filtro.setEmpresaId( null );  
		  
		BoletaPagoConsDto cons = new BoletaPagoConsDto();
		
		List<BoletaPagoDDJJConsulta> consConDDJJ = consultaService.runConDDJJ(filtro);
		cons.setCon_ddjj( mapper.mapConsConDDJJ(consConDDJJ) );

		//List<BoletaPagoBO> consSinDDJJ = consultaService.runSinDDJJ(empresaId, desde, hasta);
		//BoletaPagoBO a BoletaPagoConsSinDDJJDto
		//cons.setSin_ddjj( mapper.mapConsSinDDJJ(consSinDDJJ) );

		logger.debug("FIN" );
		return ResponseEntity.ok( cons );	 
	}
	
}
