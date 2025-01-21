package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.app.servicios.localidad.LocalidadService;
import ar.ospim.empleadores.nuevo.app.servicios.provincia.ProvinciaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.LocalidadDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.ProvinciaDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LocalidadController {
	
	private final ProvinciaService provinciaService;
	private final LocalidadService localidadService;
	
	private final ProvinciaDtoMapper provinciaMapper;
	private final LocalidadDtoMapper localidadMapper;
	
	@GetMapping( value={"/provincia", "/public/provincia"} )
	public ResponseEntity<List<ProvinciaDto>>  consultarProvincias() {
		List<ProvinciaBO> resultado = provinciaService.consultaOrderByDetalle();
		List<ProvinciaDto> rta = provinciaMapper.map(resultado);
		return ResponseEntity.ok( rta );
	}	 

	@GetMapping(value={"/provincia/{provinciaId}/localidad", "/public/provincia/{provinciaId}/localidad" } )
	public ResponseEntity<List<LocalidadDto>>  consultarProvinciaLocalidades(@PathVariable Integer provinciaId) {
		List<LocalidadDto> rta = null;
		if ( provinciaId == null ) 
			return ResponseEntity.ok( rta );
		
		List<LocalidadBO> resultado = localidadService.consultaOrderByDescripcion(provinciaId);
		rta = localidadMapper.map(resultado);
		return ResponseEntity.ok( rta );
	}	 
	


	@GetMapping(value={"/localidad", "/public/localidad"} )
	public ResponseEntity<List<LocalidadDto>>  consultarLocalidades() {
		List<LocalidadBO> resultado = localidadService.consultaOrderByDescripcion(null);
		List<LocalidadDto> rta = localidadMapper.map(resultado);
		return ResponseEntity.ok( rta );
	}	 

}
