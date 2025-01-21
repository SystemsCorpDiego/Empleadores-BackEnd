package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BancoMovimientoBO;

public interface AporteStorage {

	public List<AporteBO> findAll();   
	public List<AporteBO> findByDdjj();
	public List<AporteBO> findAllByOrderByOrdenAsc();
	
	public AporteBO save(AporteBO aporte);	
	public void deleteByCodigo(String codigo);	
	public AporteBO findByCodigo(String codigo);	
	List<AporteBO> getVigentes(LocalDate periodo);
	public AporteBO findAporteActaByEntidad(String entidad);	
	
	public BancoMovimientoBO findMovimientoByAporte(String codigo);	
	
}
