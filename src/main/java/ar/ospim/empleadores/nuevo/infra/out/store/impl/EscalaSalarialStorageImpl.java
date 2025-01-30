package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.AntiguedadDto;
import ar.ospim.empleadores.nuevo.infra.out.store.EscalaSalarialStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EscalaSalarialMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EscalaSalarialRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VEscalaSalarial;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EscalaSalarialStorageImpl implements EscalaSalarialStorage {
	private final EscalaSalarialRepository repository;
	private final EscalaSalarialMapper mapper;

	@Override
	public List<EscalaSalarialBO> findAll() {
		List<VEscalaSalarial> consulta = repository.findAll();
		return mapper.map(consulta); 		 
	}
 
	@Override
 	public String findMenorCategoriaVigente(String tipo, String camara, Integer antiguedad, LocalDate vigencia) {
 		String consulta = repository.findMenorCategoriaVigente(tipo, camara, antiguedad, vigencia);
		return consulta; 		 		
	}
	
 
	@Override
	public List<EscalaSalarialBO> findByTipoAndCamaraAndCategoriaAndAntiguedadAndVigenciaBeforeOrderByVigenciaDesc(String tipo, String camara, String categoria, Integer antiguedad, LocalDate vigencia){
		List<VEscalaSalarial> consulta = repository.findByTipoAndCamaraAndCategoriaAndAntiguedadAndVigenciaBeforeOrderByVigenciaDesc(tipo, camara, categoria, antiguedad, vigencia);
		return mapper.map(consulta); 		 
	}
 
	@Override
	public List<AntiguedadDto> getAntiguedades() {		
		return mapper.mapAntiguedad( repository.getAntiguedades() );
	}
	
}
