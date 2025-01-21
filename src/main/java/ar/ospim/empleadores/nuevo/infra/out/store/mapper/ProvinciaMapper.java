package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Provincia;

@Mapper 
public interface ProvinciaMapper {
 
	Provincia map(ProvinciaBO registro);
	ProvinciaBO map(Provincia registro);

	List<ProvinciaBO> map(List<Provincia> lst);
}

