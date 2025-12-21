package ar.ospim.empleadores.nuevo.app.servicios.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.FuncionalidadesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.RolFuncionalidadAltaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.RolFuncionalidadStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.RolStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.FuncionalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.RolFuncionalidad;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.FuncionalidadConsultaI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RolFuncionalidadServiceImpl implements RolFuncionalidadService {
    
    private final RolFuncionalidadStorage storage;
    private final RolStorage rolStorage;
    private final FuncionalidadRepository funcionalidadRepository;

    public void  actualizar(Short rolId, RolFuncionalidadAltaDto dato) {
    	log.debug("rolId: " + rolId+ " - dato: " + dato.toString());

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
		
		log.debug("FIN" );    	
    }
    
    public RolFuncionalidadAltaDto consultarRol(String rolDescrip) {
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
		return reg;
    }
    
    public List<RolFuncionalidadAltaDto> consultarTodosRol() {
		List<RolBO> consRol = rolStorage.findAll();
		List<RolFuncionalidadAltaDto> lst = cargarFuncionalidades(consRol);
		return lst;
    }
    
    public List<RolFuncionalidadAltaDto> consultarTipoUsuario() {
		List<RolBO> consRol = rolStorage.findTipoUsuario();
		List<RolFuncionalidadAltaDto> lst = cargarFuncionalidades(consRol);
		return lst;
    }

    public List<FuncionalidadesDto> getFuncionalidadesActivasByRol(String rolDescrip) {
		List<FuncionalidadesDto> lst = new ArrayList<FuncionalidadesDto>();
		FuncionalidadesDto funcDto = null;
		
		List<RolFuncionalidad>  cons2 = storage.findByRol( rolDescrip );
		if (cons2 != null && cons2.size() >0) {
			for ( RolFuncionalidad regRF : cons2) {
				if ( regRF.isActivo() ) {
					funcDto = new FuncionalidadesDto();
					funcDto.setActivo( regRF.isActivo());
					funcDto.setDescripcion( regRF.getFuncionalidad() );
					funcDto.setId(regRF.getId());
					lst.add(funcDto);
				}
			}
		}
    	return lst;
    }
    
    private List<RolFuncionalidadAltaDto> cargarFuncionalidades(List<RolBO> consRol) {
    	List<RolFuncionalidadAltaDto> lst = new ArrayList<RolFuncionalidadAltaDto>();
    	RolFuncionalidadAltaDto reg = null;
    	List<FuncionalidadesDto>  lstFunc = null;
    	for ( RolBO rol : consRol) {
			reg = new RolFuncionalidadAltaDto();
			reg.setDescripcion( rol.getDescripcion());
			reg.setId( rol.getId());
			lstFunc = getFuncionalidades( rol.getDescripcion() ) ;
			reg.setFuncionalidades( lstFunc );
			
			lst.add(reg);
		}
    	return lst; 
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
