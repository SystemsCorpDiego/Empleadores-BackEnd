package ar.ospim.empleadores.nuevo.app.servicios.mail;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.MailTipoEnvioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoEnvio;


@Service
public class MailTipoEnvioRegistrarServiceImpl implements MailTipoEnvioRegistrarService {
	
	private final MailTipoEnvioRepository repository;
	
	
	public MailTipoEnvioRegistrarServiceImpl(MailTipoEnvioRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public MailTipoEnvio run(MailTipoEnvio reg) {
		 
		return repository.save(reg);
		 
	}

}
