package ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraCategoriaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.CategoriaDto;

@Mapper 
public interface CamaraCategoriaDtoMapper {

	CamaraCategoriaBO map(CategoriaDto categoria);
	CategoriaDto map(CamaraCategoriaBO categoria);

	List<CategoriaDto> map(List<CamaraCategoriaBO> categorias);

	 
}
