package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioSeteoStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConvenioInteresServiceImpl implements ConvenioInteresService {
	
	private final ConvenioSeteoStorage storage;
	
	@Override
	public BigDecimal calcularInteres(String cuit, BigDecimal capital, LocalDate desde, LocalDate hasta) {
		
		return storage.calcularInteres(cuit, capital, desde, hasta);
		
	}

}
