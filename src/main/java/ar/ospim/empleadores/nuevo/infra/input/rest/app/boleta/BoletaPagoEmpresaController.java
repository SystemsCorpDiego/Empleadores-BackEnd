package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoActualizarService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDetalleImprimirService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoElectronicaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoImprimirService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BepDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoActualizarDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoValidaModiDto;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/empresa/{empresaId}/")
@RequiredArgsConstructor
public class BoletaPagoEmpresaController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final BoletaPagoDtoMapper mapper;
	private final BoletaPagoImprimirService imprimirService;
	private final BoletaPagoDetalleImprimirService detalleImprimirService;
	private final BoletaPagoActualizarService boletaPagoActualizar; 
	private final BoletaPagoElectronicaService bepService;
	
	
	@PutMapping(value = "/boletas/{id}")
	public ResponseEntity<Void>  actualizarBoleta(@PathVariable Integer empresaId, @PathVariable Integer id, @RequestBody BoletaPagoActualizarDto dto)  {
		BoletaPagoBO bo = mapper.map(empresaId, id, dto);
		//Modificar Boleta Pago (Fecha intencion pago y Forma Pago)
		bo = boletaPagoActualizar.run(bo);
		
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@GetMapping(value = "/boletas/{id}/validar-modi")
	public ResponseEntity<BoletaPagoValidaModiDto>  validarModiBoleta(@PathVariable Integer empresaId, @PathVariable Integer id)  {
		BoletaPagoValidaModiDto rta = new BoletaPagoValidaModiDto();
		BoletaPagoBO bo = mapper.map(empresaId, id);

		rta = boletaPagoActualizar.puedeActualizar(bo);
		
		return ResponseEntity.ok( rta );
	}	

	@GetMapping(value = "boletas/{id}/imprimir")
	public ResponseEntity<?> imprimir(@PathVariable Integer empresaId, @PathVariable Integer id) throws JRException, SQLException {
		logger.debug("empresaId: " + empresaId + "id: " + id );
		 
		logger.debug("FIN" );
		byte[] auxPdf = imprimirService.run(id);
		
		String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + "boletaPego_"+id+".pdf" + "\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(auxPdf);  
	}

	@PostMapping(value = "boletas/{id}/generar-bep")
	public ResponseEntity<BepDto> generarPagoElectronico(@PathVariable Integer empresaId, @PathVariable Integer id) throws JRException, SQLException {
		BepDto rta = new BepDto();		
		String bep = "";
		bep = bepService.runAndSave(id);
		if ( bep.startsWith("ERROR") ) {
			rta.setError(bep);
		} else {
			rta.setBep(bep);
		}		
		
		//rta.setBep(null);
		//rta.setError("ERROR de prueba");
		return ResponseEntity.ok( rta );
	}

	@GetMapping(value = "boletas/{id}/imprimir-detalle")
	public ResponseEntity<?> imprimirDetalle(@PathVariable Integer empresaId, @PathVariable Integer id) throws JRException, SQLException {
		logger.debug("empresaId: " + empresaId + "id: " + id );
		 
		logger.debug("FIN" );
		byte[] auxPdf = detalleImprimirService.run(id);
		
		String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + "boletaPegoDetalle_" +id+ ".pdf" + "\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(auxPdf);  
	}
	
 
}
