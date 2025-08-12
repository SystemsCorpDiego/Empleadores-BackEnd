package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;

public interface AporteSeteoStorage {
	
	public AporteSeteoBO save(AporteSeteoBO aporte);
	public void deleteById(Integer id);	
	
	public AporteSeteoBO findVigente(String codigo, LocalDate periodo);
	
	public List<AporteSeteo> getVigentes(LocalDate periodo);
	
	Optional<AporteSeteo> findContenido(String entidad, String aporte, LocalDate desde);
	Optional<AporteSeteo> findContenido(String entidad, String aporte, LocalDate desde, Integer id);
}
