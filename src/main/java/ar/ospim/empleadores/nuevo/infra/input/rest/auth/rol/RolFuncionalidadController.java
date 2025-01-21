package ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.FuncionalidadesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.RolFuncionalidadAltaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.RolFuncionalidadStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.RolStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.FuncionalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.RolFuncionalidad;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.FuncionalidadConsultaI;
import lombok.RequiredArgsConstructor;

@RestController //"roles/{rolId}", 
@RequestMapping(value = {"roles/funcionalidades", "funcionalidades"})
@RequiredArgsConstructor
public class RolFuncionalidadController {
    private static final Logger LOG = LoggerFactory.getLogger(RolFuncionalidadController.class);
    private final RolFuncionalidadStorage storage;
    private final RolStorage rolStorage;
    private final FuncionalidadRepository funcionalidadRepository;
	
	@PutMapping(value = { "/actualizar/{rolId}"})
	public ResponseEntity<?>  actualizar(@PathVariable Short rolId, @RequestBody @Valid RolFuncionalidadAltaDto dato) {
		LOG.debug("rolId: " + rolId+ " - dato: " + dato.toString());

		RolFuncionalidad reg = null;
		if ( dato != null && rolId != null) {
			Optional<RolBO> consRol = rolStorage.findById( rolId  );
			if ( consRol.isPresent() &&  dato.getFuncionalidades() != null && dato.getFuncionalidades().size() >0 ) {
				for ( FuncionalidadesDto func : dato.getFuncionalidades() ) {
					reg = new RolFuncionalidad();
					reg.setRol( consRol.get().getDescripcion() );
					reg.setFuncionalidad(func.getDescripcion() );
					reg.setActivo( func.isActivo() );
					
					storage.save(reg);
				}
			}
		}
		
		LOG.debug("FIN" );
		return ResponseEntity.noContent( ).build();
	}
	
	@GetMapping( "/{rolDescrip}" )
	public ResponseEntity<RolFuncionalidadAltaDto> consultarRol(@PathVariable String rolDescrip) {
		RolFuncionalidadAltaDto reg = null;
		List<FuncionalidadesDto>  lstFunc = null;

		if ( rolDescrip != null ) {
			Optional<RolBO>  consRol = rolStorage.findByDescripcion(rolDescrip);
			if ( consRol.isPresent() ) {
				reg = new RolFuncionalidadAltaDto();
				reg.setDescripcion(consRol.get().getDescripcion());
				reg.setId( consRol.get().getId() );
				lstFunc = getFuncionalidades( consRol.get().getDescripcion() ) ;
				
				reg.setFuncionalidades(lstFunc);
			}
		}
		return ResponseEntity.ok(reg) ;	
	}

	@GetMapping( "" )
	public ResponseEntity<List<RolFuncionalidadAltaDto>> consultarTodosRol() {
		List<RolFuncionalidadAltaDto> lst = new ArrayList<RolFuncionalidadAltaDto>();
		RolFuncionalidadAltaDto reg = null;
		List<FuncionalidadesDto>  lstFunc = null;
		
		List<RolBO> consRol = rolStorage.findAll();
		for ( RolBO rol : consRol) {
			reg = new RolFuncionalidadAltaDto();
			reg.setDescripcion( rol.getDescripcion());
			reg.setId( rol.getId());
			lstFunc = getFuncionalidades( rol.getDescripcion() ) ;
			reg.setFuncionalidades( lstFunc );
			
			lst.add(reg);
		}
		 
		return ResponseEntity.ok(lst) ;	
	}

	private List<FuncionalidadesDto> getFuncionalidades(String rolDescrip) {
		List<FuncionalidadesDto> lst = new ArrayList<FuncionalidadesDto>();
		FuncionalidadesDto funcDto = null;
		
		List<RolFuncionalidad>  cons2 = storage.findByRol( rolDescrip );
		if (cons2 != null && cons2.size() >0) {
			for ( RolFuncionalidad regRF : cons2) {
				funcDto = new FuncionalidadesDto();
				funcDto.setActivo( regRF.isActivo());
				funcDto.setDescripcion( regRF.getFuncionalidad() );
				funcDto.setId(regRF.getId());
				lst.add(funcDto);
			}
		}
		List<FuncionalidadConsultaI>  lstFunc = funcionalidadRepository.getFaltanteRol( rolDescrip );
		for ( FuncionalidadConsultaI reg: lstFunc) {
			funcDto = new FuncionalidadesDto();
			funcDto.setActivo( false );
			funcDto.setDescripcion( reg.getDescripcion() );
			funcDto.setId( reg.getId() );
			lst.add(funcDto);
		}
		
		return lst;
	}
	
}
