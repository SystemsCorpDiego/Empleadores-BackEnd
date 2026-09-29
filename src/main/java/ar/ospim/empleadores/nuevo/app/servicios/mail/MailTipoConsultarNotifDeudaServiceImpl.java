package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.dominio.MailTipoDeudaInfoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IMailTipoDeudaInfoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;


@Service
public class MailTipoConsultarNotifDeudaServiceImpl implements MailTipoConsultarNotifDeudaService {

	private final DeudaNominaRepository repository;
	private final MailTipoDeudaInfoMapper mapper;
	
	public MailTipoConsultarNotifDeudaServiceImpl(
			DeudaNominaRepository repository,
			MailTipoDeudaInfoMapper mapper) {
		super();
		this.repository = repository;
		this.mapper= mapper;
	}


	@Override
	public List<MailTipoDeudaInfoBO> run() {
		
		List<IMailTipoDeudaInfoDto> lst = repository.getMailTipoNotificacionesDeudaNomina();
		List<MailTipoDeudaInfoBO> lstRta = mapper.run(lst);
		
		return lstRta;
	}

}
