package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.dominio.MailTipoDdjjPendienteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.IMailTipoDdjjPendienteDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJRepository;

@Service
public class MailTipoConsultarNotifDdjjPendienteServiceImpl implements MailTipoConsultarNotifDdjjPendienteService {

	private final DDJJRepository repository;
	private final MailTipoDdjjPendienteMapper mapper;
	
	public MailTipoConsultarNotifDdjjPendienteServiceImpl(
			DDJJRepository repository,
			MailTipoDdjjPendienteMapper mapper) {
		super();
		this.repository = repository;
		this.mapper= mapper;
	}


	@Override
	public List<MailTipoDdjjPendienteBO> run() {
		//cuit , razon_social , mail , periodo 
		List<IMailTipoDdjjPendienteDto> lst = repository.getMailTipoNotificacionesDdjjPendiente();
		List<MailTipoDdjjPendienteBO> lstRta = mapper.run(lst);
		
		return lstRta;
	}

}
