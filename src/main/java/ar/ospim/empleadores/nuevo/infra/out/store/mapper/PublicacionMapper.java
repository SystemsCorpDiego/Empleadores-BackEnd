package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Publicacion;

@Mapper 
public interface PublicacionMapper {

	Publicacion map(PublicacionBO feriado);
	PublicacionBO map(Publicacion feriado);
	
	@Mapping( target="id", ignore = true)  
	void map(PublicacionBO publicacion, @MappingTarget Publicacion publicacionNew);

	List<PublicacionBO> map(List<Publicacion> lstPublicacion);

}

