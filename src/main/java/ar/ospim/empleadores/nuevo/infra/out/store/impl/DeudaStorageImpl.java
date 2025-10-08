package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.DeudaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ActaMolinerosRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ActaMolinerosI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DeudaStorageImpl  implements DeudaStorage {

	//@Autowired
	//private DataSource dataSource; 

	@Autowired
	private ActaMolinerosRepository ActaRepository;
	@Autowired
	private DeudaNominaRepository nominaRepository; 
	
	
	public List<ActaMolinerosI> getActasMolineros2(String cuit, String entidad) {
		List<ActaMolinerosI>  rta = null;
		
		try {
			 rta = ActaRepository.getByCuitAndEntidad2(cuit, entidad); //"30537582916"
				if ( rta != null ) {
					log.error("lst NO NULAAA !! - lst.size(): " + rta.size());					
				}			 
		} catch ( Exception e) {
			log.debug( e.toString() );			
		}
		
		return rta;
	}
	
	
	public List<ActaMolineros> getActasMolineros(String cuit, String entidad) {
		List<ActaMolineros>  rta = null;
		
		try {
			 rta = ActaRepository.getByCuitAndEntidad(cuit, entidad); //"30537582916"
				if ( rta != null ) {
					log.error("lst NO NULAAA !! - lst.size(): " + rta.size());					
				}			 
		} catch ( Exception e) {
			log.debug( e.toString() );			
		}
		
		return rta;
	}
	 
	
	public List<IGestionDeudaDDJJDto> getNominaDto(String cuit, String entidad){
		 List<IGestionDeudaDDJJDto> rta = null;
		 try {			 
			 log.error("DeudaStorageImpl.getNominaDto() - cuit: " + cuit + " - entidad: " + entidad);
			 rta = nominaRepository.get(cuit, entidad);
			 log.error("DeudaStorageImpl.getNominaDto() - rta: " + rta );
		 }  catch ( Exception e) {
			 log.debug( e.toString() );			
		 }
		 return rta;
	}
	
	/*
	public List<IGestionDeudaDDJJDto> getNominaDto(String cuit) {
		List<IGestionDeudaDDJJDto> rta = null;
		try {
			rta = nominaRepository.get(cuit);
		} catch ( Exception e) {
			 log.debug( e.toString() );			
		 }
		return rta;
	}
	*/
	
	public void actualizarCuit( String p_cuit ) {
		nominaRepository.actualizarCuit(p_cuit);
	}
}
