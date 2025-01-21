package ar.ospim.empleadores.nuevo.app.servicios.escalasalarial;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.AntiguedadDto;

public interface EscalaSalarialService {

	public List<EscalaSalarialBO> get(String tipo, String camara, String categoria, Integer antiguedad, LocalDate vigencia);
	
	public List<AntiguedadDto> getAntiguedades();
	
}
