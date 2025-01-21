package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Localidad;

@Mapper
public interface LocalidadMapper {

	Localidad map(LocalidadBO registro);
	
	@Mapping( target="descripcion", source = "detalle")  
	LocalidadBO map(Localidad registro);

	
	List<LocalidadBO> map(List<Localidad> lst);

}
