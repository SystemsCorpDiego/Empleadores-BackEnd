package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.DeudaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/empresa/{empresaId}/deuda")
@RequiredArgsConstructor
public class DeudaController {
	
	private final  DeudaService deudaService;
	private final  DeudaMapper mapper;
	
	@GetMapping(value = "")
	public ResponseEntity<GestionDeudaDto>  get(@PathVariable("empresaId") Integer empresaId) {
		GestionDeudaDto rta = null;
		
		List<ActaMolineros> lstActas = deudaService.getMolinerosActas(empresaId);
		
		rta = new  GestionDeudaDto();
		rta.setActas( mapper.run(lstActas) );
		rta.setDeclaracionesJuradas( mapper.runNomina2(deudaService.getDDJJDto(empresaId))  );

		return ResponseEntity.ok( rta );
	}

	@GetMapping(value = "/entidad/{entidadCodigo}")
	public ResponseEntity<GestionDeudaDto>  get(@PathVariable("empresaId") Integer empresaId, @PathVariable("entidadCodigo") String entidadCodigo) {
		GestionDeudaDto rta = null;
		
		List<ActaMolineros> lstActas = deudaService.getMolinerosActas(empresaId, entidadCodigo);
		rta = new  GestionDeudaDto();
		rta.setActas( mapper.run(lstActas) );
		
		rta.setDeclaracionesJuradas( mapper.runNomina2(deudaService.getDDJJDto(empresaId, entidadCodigo))  );
		
		rta.setSaldosAFavor( deudaService.getAjustesDto(empresaId, entidadCodigo) );
		
		return ResponseEntity.ok( rta );
	}
	
}
