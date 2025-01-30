package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.AntiguedadDto;

public interface EscalaSalarialStorage {

	public List<EscalaSalarialBO> findAll();
 	public List<EscalaSalarialBO> findByTipoAndCamaraAndCategoriaAndAntiguedadAndVigenciaBeforeOrderByVigenciaDesc(String tipo, String camara, String categoria, Integer antiguedad, LocalDate vigencia);
 	public String findMenorCategoriaVigente(String tipo, String camara, Integer antiguedad, LocalDate vigencia);
 	public List<AntiguedadDto> getAntiguedades(); 
 	
}
