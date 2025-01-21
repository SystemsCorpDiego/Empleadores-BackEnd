package ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.CamaraDto;

@Mapper 
public interface CamaraDtoMapper {

	CamaraBO map(CamaraDto feriado);
	CamaraDto map(CamaraBO feriado);

	List<CamaraDto> map(List<CamaraBO> feriados);
 
}
