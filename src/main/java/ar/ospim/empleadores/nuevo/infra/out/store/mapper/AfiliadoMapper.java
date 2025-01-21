package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado;

@Mapper
public interface AfiliadoMapper {

	AfiliadoBO map(Afiliado reg);
	List<AfiliadoBO> map(List<Afiliado> reg);
	
	Afiliado map(AfiliadoBO reg);
	
}
