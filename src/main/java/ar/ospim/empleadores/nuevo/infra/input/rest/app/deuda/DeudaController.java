package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("deuda")
@RequiredArgsConstructor
public class DeudaController {
	
	private final  DeudaService deudaService;
	private final  DeudaMapper mapper;
	
	@GetMapping(value = "/empresa/{empresaId}")
	public ResponseEntity<GestionDeudaDto>  get(@PathVariable("empresaId") Integer empresaId) {
		GestionDeudaDto rta = null;
		
		List<ActaMolineros> lstActas = deudaService.getMolinerosActas(empresaId);
		//List<DeudaNomina> lstNomina = deudaService.getDDJJ(empresaId);
		
		rta = new  GestionDeudaDto();
		rta.setActas( mapper.run(lstActas) );
		rta.setDeclaracionesJuradas( mapper.runNomina2(deudaService.getDDJJDto(empresaId))  );
		//rta.setDeclaracionesJuradas( mapper.runNomina(lstNomina) );
		//deudaService.actualizarSecuencia( rta.getDeclaracionesJuradas() );
		//deudaService.actualizarAporteDescrip( rta.getDeclaracionesJuradas() );
		
		
		
		return ResponseEntity.ok( rta );
	}

}
