package ar.ospim.empleadores.nuevo.app.servicios.escalasalarial;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.AntiguedadDto;
import ar.ospim.empleadores.nuevo.infra.out.store.EscalaSalarialStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EscalaSalarialServiceImpl implements EscalaSalarialService {
	
	private final EscalaSalarialStorage storage;
	
	
	@Override
	public List<EscalaSalarialBO> get(String tipo, String camara, String categoria, Integer antiguedad, LocalDate vigencia) {
		List<EscalaSalarialBO> cons = storage.findByTipoAndCamaraAndCategoriaAndAntiguedadAndVigenciaBeforeOrderByVigenciaDesc(tipo, camara, categoria, antiguedad, vigencia);
		
		return cons;
	}
	

	@Override
	public String getMenorCategoriaVigente(String tipo, String camara, Integer antiguedad, LocalDate vigencia) {
		
		String cons = storage.findMenorCategoriaVigente(tipo, camara, antiguedad, vigencia); 		
		return cons;		
	}
	
	@Override
	public List<AntiguedadDto> getAntiguedades() {
		return storage.getAntiguedades();
	}
	
	
}
