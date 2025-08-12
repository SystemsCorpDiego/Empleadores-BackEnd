package ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteSeteoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte.dto.AporteSeteoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor 
public class AporteSeteoController {
	
	private final MessageSource messageSource;
	private final AporteSeteoService service;
	private final AporteSeteoDtoMapper mapper;

	@GetMapping(value = "/aportes/seteos/vigentes")
	public ResponseEntity<List<AporteSeteo>>  consultarVigentes(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE )  @Nullable @RequestParam LocalDate periodo) {
		if ( periodo == null) {			
			periodo = LocalDate.now();			
		}
		
		List<AporteSeteo> consulta = service.findVigentes(periodo);
		 
		return ResponseEntity.ok( consulta );
	}
	
	@PostMapping(value = "/aportes/seteos")
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid AporteSeteoDto dato, HttpServletRequest request) {
		setearNulos( dato );
 		
		if ( dato.getCalculoValor()  != null   ) {
			try {
				new BigDecimal( dato.getCalculoValor() ); 
			} catch (Exception e) {
				String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_BIGDECIMAL.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.ATRIBUTO_BIGDECIMAL.name(), String.format(errorMsg, "Valor de Calculo", dato.getCalculoValor()) );			
			}
			
		}

			
		AporteSeteoBO registro = mapper.map(dato);
		registro.setId(null);
		registro = service.guardar(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/aportes/seteos/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer id, @RequestBody @Valid AporteSeteoDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		setearNulos( dato );
		
		AporteSeteoBO registro = mapper.map(dato, id);
		registro = service.guardar(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@DeleteMapping(value = "/aportes/seteos/{id}")
	public ResponseEntity<Void>  borrar( @PathVariable Integer id ) {
		service.borrar(id);
		return ResponseEntity.noContent().<Void>build(); 
	}

	private void setearNulos(AporteSeteoDto dato) {
		if ( "".equals( dato.getCalculoValor() ) ) 
			dato.setCalculoValor(null); 
		if (dato.getCalculoBase() != null && dato.getCalculoBase().equals("") ) 
			dato.setCalculoBase(null);  
		if ( "".equals( dato.getCamara() ) ) 
			dato.setCamara(null);
		if ( "".equals( dato.getCamaraCategoria() ) ) 
			dato.setCamaraCategoria(null);
		if ( "".equals( dato.getCamaraAntiguedad() ) ) 
			dato.setCamaraAntiguedad(null); 
		
	}
}
