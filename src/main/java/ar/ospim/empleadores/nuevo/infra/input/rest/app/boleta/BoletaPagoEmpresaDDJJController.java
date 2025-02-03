package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJCrearService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.DDJJBoletaPagoAltaDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa/{empresaId}/ddjj/{ddjjId}/boletas")
 public class BoletaPagoEmpresaDDJJController {
 
	private final BoletaPagoDtoMapper mapper;
	private final BoletaPagoDDJJCrearService boletaPagoDDJJCrear;
	
	
	@PostMapping(value = "/generar")
	public ResponseEntity<Void>  generarBoletasPagoDDJJ(@PathVariable Integer empresaId, @PathVariable Integer ddjjId, @RequestBody List<DDJJBoletaPagoAltaDto> lstDto)  {
		List<BoletaPagoBO> lstBO = mapper.map(lstDto);
		
		boletaPagoDDJJCrear.run(ddjjId, lstBO);
		
		return ResponseEntity.noContent().<Void>build(); 
	}	
	
	
}
