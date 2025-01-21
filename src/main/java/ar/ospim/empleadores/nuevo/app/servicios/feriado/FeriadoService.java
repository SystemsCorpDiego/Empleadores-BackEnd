package ar.ospim.empleadores.nuevo.app.servicios.feriado;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;

public interface FeriadoService {

	public List<FeriadoBO> consultarAnio(Integer anio);
	
	public List<FeriadoBO> consultar();   
	public FeriadoBO guardar(FeriadoBO feriado);	
	public void borrar(Integer feriadoId);	

	public LocalDate obtenerSiguienteDiaHabil(LocalDate dia);
	
	public List<FeriadoBO> getFeriadosDesde(LocalDate fecha);
	
	
	public void duplicarAnio(Integer anio);
	
	
}
