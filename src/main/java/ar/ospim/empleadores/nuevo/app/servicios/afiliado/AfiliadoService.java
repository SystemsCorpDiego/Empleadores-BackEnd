package ar.ospim.empleadores.nuevo.app.servicios.afiliado;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfiliadoActu;

public interface AfiliadoService {

	public List<AfiliadoBO> get(String cuil);
	
	public AfiliadoBO registrar(AfiliadoBO reg);
	
	public AfiliadoActu saveActu(AfiliadoActu reg);
	
}
