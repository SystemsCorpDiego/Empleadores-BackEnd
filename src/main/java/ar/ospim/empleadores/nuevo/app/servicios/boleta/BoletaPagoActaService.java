package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;

public interface BoletaPagoActaService {	
	
	public Optional<BoletaPagoBO> buscar(Integer id);
	BoletaPagoBO guardar(BoletaPagoBO boleta);
	public void borrar(Integer empresaId, Integer id);
	public Boolean ddjjConBoletas(Integer ddjjId);
	
}