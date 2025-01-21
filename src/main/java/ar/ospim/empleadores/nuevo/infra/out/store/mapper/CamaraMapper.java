package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamara;

@Mapper 
public interface CamaraMapper {

	VCamara map(CamaraBO feriado);
	CamaraBO map(VCamara feriado);

	List<CamaraBO> map(List<VCamara> feriados);
}
