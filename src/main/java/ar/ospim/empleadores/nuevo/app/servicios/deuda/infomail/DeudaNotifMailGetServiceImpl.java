package ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaMailInfoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IDeudaNominaNotifMailDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;


@Service
public class DeudaNotifMailGetServiceImpl implements DeudaNotifMailGetService {

	private final DeudaNominaRepository repository;
	private final DeudaNominaNotifMailMapper mapper;
	
	public DeudaNotifMailGetServiceImpl(
			DeudaNominaRepository repository,
			DeudaNominaNotifMailMapper mapper) {
		super();
		this.repository = repository;
		this.mapper= mapper;
	}


	@Override
	public List<DeudaMailInfoBO> run() {
		
		List<IDeudaNominaNotifMailDto> lst = repository.getDeudaNominaNotifMail();
		List<DeudaMailInfoBO> lstRta = mapper.run(lst);
		
		return lstRta;
	}

}
