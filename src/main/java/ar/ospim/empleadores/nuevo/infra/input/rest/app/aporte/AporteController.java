package ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.CodigoGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte.dto.AporteDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("aportes")
@RequiredArgsConstructor 
public class AporteController {
		
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		private final AporteService service;
		private final AporteDtoMapper mapper;

		
		@GetMapping
		public ResponseEntity<List<AporteDto>>  consultar() {
			List<AporteBO> consulta = service.consultar();
			List<AporteDto> consultaDto = mapper.map(consulta) ;
			return ResponseEntity.ok( consultaDto );
		}
		
		@GetMapping("/ddjj")
		public ResponseEntity<List<AporteDto>>  consultarDdjj() {
			List<AporteBO> consulta = service.consultarDDJJ();
			List<AporteDto> consultaDto = mapper.map(consulta) ;
			return ResponseEntity.ok( consultaDto );
		}
		
		@PostMapping
		public ResponseEntity<CodigoGeneradoDto>  agregar( @RequestBody @Valid AporteDto dato, HttpServletRequest request) {
			
			AporteBO registro = mapper.map(dato);
			registro = service.guardar(registro);		
			CodigoGeneradoDto rta = new CodigoGeneradoDto(registro.getCodigo());
			
			URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getCodigo()) );
	 
			return ResponseEntity.created( location ).body(rta);
		}
		
		@PutMapping(value = "/{codigo}")
		public ResponseEntity<Void>  actualizar(@PathVariable String codigo, @RequestBody @Valid AporteDto dato) {
			logger.debug("codigo: " + codigo + " - dato: " + dato.toString());
			
			AporteBO registro = mapper.map(dato, codigo);
			registro = service.guardar(registro);
			
			logger.debug("FIN" );
			return ResponseEntity.noContent().<Void>build(); 
		}
		
		@DeleteMapping(value = "/{codigo}")
		public ResponseEntity<Void>  borrar( @PathVariable String codigo ) {
			service.borrar(codigo);
			return ResponseEntity.noContent().<Void>build(); 
		}
}
