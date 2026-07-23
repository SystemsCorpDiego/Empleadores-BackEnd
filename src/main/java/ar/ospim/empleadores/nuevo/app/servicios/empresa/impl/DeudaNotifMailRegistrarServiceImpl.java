package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.servicios.empresa.DeudaNotifMailRegistrarService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaMailEnvioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailEnvio;


@Service
public class DeudaNotifMailRegistrarServiceImpl implements DeudaNotifMailRegistrarService {
	
	private final DeudaNominaMailEnvioRepository repository;
	
	
	public DeudaNotifMailRegistrarServiceImpl(DeudaNominaMailEnvioRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public DeudaNominaMailEnvio run(DeudaNominaMailEnvio reg) {
		 
		return repository.save(reg);
		 
	}

}
