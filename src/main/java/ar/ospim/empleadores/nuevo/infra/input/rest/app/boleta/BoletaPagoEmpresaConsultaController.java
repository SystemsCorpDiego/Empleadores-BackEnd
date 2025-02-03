package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJConsultaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoConsConDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoConsDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/boletas")
@RequiredArgsConstructor
public class BoletaPagoEmpresaConsultaController {

	private final BoletaPagoConsultaService consultaService;
	private final BoletaPagoDDJJConsultaService boletaPagoConsultaService;
	private final BoletaPagoDtoMapper mapper;
	
	@GetMapping("/consulta-gral")
	public ResponseEntity<BoletaPagoConsDto>  consultar(@PathVariable Integer empresaId, 
			@Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde, 
			@Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

		BoletaPagoConsDto cons = new BoletaPagoConsDto();
		BoletaPagoFiltroDto filtro = new BoletaPagoFiltroDto();
		filtro.setEmpresaId(empresaId);
		filtro.setPeriodoDesde(desde);
		filtro.setPeriodoHasta(hasta);
		
		List<BoletaPagoDDJJConsulta> consConDDJJ = consultaService.runConDDJJ(filtro);
		cons.setCon_ddjj( mapper.mapConsConDDJJ(consConDDJJ) );

		List<BoletaPagoBO> consSinDDJJ = consultaService.runSinDDJJ(empresaId, desde, hasta);
		//BoletaPagoBO a BoletaPagoConsSinDDJJDto
		cons.setSin_ddjj( mapper.mapConsSinDDJJ(consSinDDJJ) );

		log.debug("FIN" );
		return ResponseEntity.ok( cons );	 
	}
	
	
	@GetMapping("/{boletaId}/consulta-gral/conDdjj")
	public ResponseEntity<BoletaPagoConsConDDJJDto>  consultarBoleta(@PathVariable Integer empresaId, @PathVariable Integer boletaId ) {
		BoletaPagoConsConDDJJDto reg = new BoletaPagoConsConDDJJDto();
		BoletaPagoDDJJConsulta consConDDJJ = consultaService.runConDDJJ(boletaId);
		reg = mapper.map(consConDDJJ);
		
		return ResponseEntity.ok( reg );
	}
		
	@GetMapping("/{boletaId}")
	public ResponseEntity<DDJJBoletaArmadoDetalleDto>  consultar(@PathVariable Integer empresaId, @PathVariable Integer boletaId ) {
		DDJJBoletaArmadoDetalleDto dto = boletaPagoConsultaService.run(boletaId); 
		return ResponseEntity.ok( dto );
	}
	
}
