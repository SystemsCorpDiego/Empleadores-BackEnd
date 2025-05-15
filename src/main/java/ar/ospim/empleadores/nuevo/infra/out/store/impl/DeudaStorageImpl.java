package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.DeudaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ActaMolinerosRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;
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
	
	public List<ActaMolineros> getActasMolineros(String cuit) {
		List<ActaMolineros>  rta = null;
		
		try {
			 rta = ActaRepository.getByCuit(cuit); //"30537582916"
				if ( rta != null ) {
					log.error("lst NO NULAAA !! - lst.size(): " + rta.size());					
				}			 
		} catch ( Exception e) {
			log.debug( e.toString() );			
		}
		
		return rta;
	}
	
	public List<DeudaNomina> getNomina(String cuit) {
		 List<DeudaNomina> rta = null;
		 try {
			 rta = nominaRepository.getByCuitAndActaIdIsNull(cuit);
		 }  catch ( Exception e) {
			 log.debug( e.toString() );			
		 }
		 return rta;
	}
	
	public List<IGestionDeudaDDJJDto> getNominaDto(String cuit) {
		List<IGestionDeudaDDJJDto> rta = null;
		try {
			rta = nominaRepository.get(cuit);
		} catch ( Exception e) {
			 log.debug( e.toString() );			
		 }
		return rta;
	}
	
}
