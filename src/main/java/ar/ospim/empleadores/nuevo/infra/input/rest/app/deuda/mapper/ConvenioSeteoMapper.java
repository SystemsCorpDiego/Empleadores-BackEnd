package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioSeteoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;

@Mapper
public interface ConvenioSeteoMapper {

	List<ConvenioSeteoDto> run(List<ConvenioSeteo> reg);
	ConvenioSeteoDto run(ConvenioSeteo reg);
	ConvenioSeteo run(ConvenioSeteoDto reg);

}
