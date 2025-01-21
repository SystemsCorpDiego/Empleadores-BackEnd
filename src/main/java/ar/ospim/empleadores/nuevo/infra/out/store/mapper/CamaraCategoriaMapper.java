package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraCategoriaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamaraCategoria;

@Mapper 
public interface CamaraCategoriaMapper {

	
	  @Mapping(source = "pk.camara", target = "camara")
	  @Mapping(source = "pk.categoria", target = "categoria")
	  CamaraCategoriaBO map(VCamaraCategoria feriado);

		@InheritInverseConfiguration
		VCamaraCategoria map(CamaraCategoriaBO feriado);
		

	List<CamaraCategoriaBO> map(List<VCamaraCategoria> feriados);
}
