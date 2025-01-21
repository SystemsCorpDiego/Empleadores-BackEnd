package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJBoletaPagoArmadoService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaConInteresArmarDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPeriodoInfoDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/empresa/{empresaId}/ddjj")
@RequiredArgsConstructor
public class DDJJEmpresaConsultasController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DDJJBoletaPagoArmadoService ddjjBoletaArmadoService; 
	private final DDJJConsultarService consultarService; 

	@RequestMapping(value = "/{ddjjId}/boletas/armar", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<DDJJBoletaArmadoDto>  consultarDDJJArmadoBoletas(@PathVariable Integer empresaId, @PathVariable Integer ddjjId, @Nullable @RequestBody DDJJBoletaConInteresArmarDto dto) {
		logger.debug( "empresaId: " + empresaId.toString());		 
		DDJJBoletaArmadoDto cons =null;
		if ( dto != null && dto.getIntencionDePago() != null) {
			cons = ddjjBoletaArmadoService.run(ddjjId, null, dto.getIntencionDePago() );
		} else {
			cons = ddjjBoletaArmadoService.run(ddjjId, null, null);
		}
		
		logger.debug("FIN" );
		//cons.getBoletas().get(3).getAjuste();
		return ResponseEntity.ok( cons );	 
	}
	
	@RequestMapping(value = "/{ddjjId}/boletas/{aporteCodigo}/armar", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<DDJJBoletaArmadoDto>  consultarDDJJArmadoBoletas(@PathVariable Integer empresaId, @PathVariable Integer ddjjId, @PathVariable String aporteCodigo, @Nullable @RequestBody DDJJBoletaConInteresArmarDto dto) {
		logger.debug( "empresaId: " + empresaId.toString());		 
		DDJJBoletaArmadoDto cons =null;
		
		cons = ddjjBoletaArmadoService.run( ddjjId, aporteCodigo, dto.getIntencionDePago() );
		
		logger.debug("FIN" );
		return ResponseEntity.ok( cons );	 
	}
	
	@RequestMapping(value = "/periodo/{periodo}/info", method = RequestMethod.GET)
	public ResponseEntity<DDJJPeriodoInfoDto>  consultarInfoPeriodo(@PathVariable Integer empresaId, @PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate periodo) {
		DDJJPeriodoInfoDto rta = null;
		rta = consultarService.consultarInfoPeriodo(empresaId, periodo);
		
		return ResponseEntity.ok( rta );	
	}
}
