package ar.ospim.empleadores.nuevo.app.servicios.aporte;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;

public interface AporteService {
	
	public List<AporteBO> consultar();   

	public List<AporteBO> consultarDDJJ();   
	
	public List<AporteBO> consultarOrderByOrdenAsc();

	public AporteBO findByCodigo(String codigo);   

	public AporteBO guardar(AporteBO feriado);	
	
	public void borrar(String  codigo);	

}
