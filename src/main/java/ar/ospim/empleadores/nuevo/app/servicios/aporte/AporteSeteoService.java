package ar.ospim.empleadores.nuevo.app.servicios.aporte;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;

public interface AporteSeteoService {

	public AporteSeteoBO guardar(AporteSeteoBO aporteSeteo);	
	
	public void borrar(Integer id);	

	
	public List<AporteSeteo> findVigentes( LocalDate periodo );
	
}
