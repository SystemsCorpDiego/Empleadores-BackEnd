package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Feriado;

@Mapper 
public interface FeriadoMapper {
 
	Feriado map(FeriadoBO feriado);
	FeriadoBO map(Feriado feriado);
	
	@Mapping( target="id", ignore = true)  
	void map(FeriadoBO feriado, @MappingTarget Feriado feriadoNew);

	List<FeriadoBO> map(List<Feriado> feriados);

}

