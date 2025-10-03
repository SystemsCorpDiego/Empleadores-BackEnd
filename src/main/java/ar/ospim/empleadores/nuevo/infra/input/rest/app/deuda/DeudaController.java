package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IDeudaNominaDescargaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.DeudaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ActaMolinerosI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeudaController {
	
	private final  DeudaService deudaService;
	private final  DeudaMapper mapper;
	
	/*
	 * TODO: ver si se usa. Hay que agregarle ENTIDAD a esto. 
	@GetMapping(value = "/empresa/{empresaId}/deuda")
	public ResponseEntity<GestionDeudaDto>  get(@PathVariable("empresaId") Integer empresaId) {
		GestionDeudaDto rta = null;
		
		List<ActaMolineros> lstActas = deudaService.getMolinerosActas(empresaId);
		
		rta = new  GestionDeudaDto();
		rta.setActas( mapper.run(lstActas) );
		rta.setDeclaracionesJuradas( mapper.runNomina2(deudaService.getDDJJDto(empresaId))  );

		return ResponseEntity.ok( rta );
	}
	*/

	@GetMapping(value = "/empresa/{empresaId}/deuda/entidad/{entidadCodigo}")
	public ResponseEntity<GestionDeudaDto>  get(@PathVariable("empresaId") Integer empresaId, @PathVariable("entidadCodigo") String entidadCodigo) {
		//Gestion Deuda: Consulta deuda desde Snapshot para un CUIT+Entidad
		GestionDeudaDto rta = null;
		
		List<ActaMolinerosI> lstActas = deudaService.getMolinerosActas2(empresaId, entidadCodigo);
		rta = new  GestionDeudaDto();
		rta.setActas( mapper.run3(lstActas) );		 
		
		List<IGestionDeudaDDJJDto> lst = deudaService.getDDJJDto(empresaId, entidadCodigo);
		List<GestionDeudaDDJJDto> lst2 =  mapper.runNomina2(lst);
		lst2 =   mapper.runCastIdString(lst2 );
		
		if (lst2 == null )
			lst2 = new ArrayList<GestionDeudaDDJJDto>();
		
		rta.setDeclaracionesJuradas( lst2  );
		//rta.setDeclaracionesJuradas( mapper.runNomina2(deudaService.getDDJJDto(empresaId, entidadCodigo))  );
		
		List<IGestionDeudaAjustesDto> iLst =  deudaService.getAjustesDto(empresaId, entidadCodigo);
		List<GestionDeudaAjustesDto> lst3 = mapper.run2(iLst);
		
		for(GestionDeudaAjustesDto reg :  lst3) {
			reg.setImporte( reg.getImporte( ).multiply( BigDecimal.valueOf(-1)) );
		}
		
		rta.setSaldosAFavor( lst3  );
		
		return ResponseEntity.ok( rta );
	}
	
	
	@GetMapping(value = "/deuda/")
	public ResponseEntity<List<IDeudaNominaDescargaDto>> getDeudaNominaCarteraCompleta() {
		
		return ResponseEntity.ok( deudaService.getDeudaNominaAll() );
				
	}
	
}
