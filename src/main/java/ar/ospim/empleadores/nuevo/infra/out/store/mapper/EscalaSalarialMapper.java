package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.AntiguedadDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VEscalaSalarial;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.EscalaSalarialAntiguedadI;

@Mapper 
public interface EscalaSalarialMapper {

	VEscalaSalarial map(EscalaSalarialBO feriado);
	EscalaSalarialBO map(VEscalaSalarial feriado);

	List<EscalaSalarialBO> map(List<VEscalaSalarial> feriados);
	
	
	AntiguedadDto map(EscalaSalarialAntiguedadI reg); 
	
	List<AntiguedadDto> mapAntiguedad(List<EscalaSalarialAntiguedadI> lst);
	
}
