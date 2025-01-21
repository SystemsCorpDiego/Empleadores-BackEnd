package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.DomicilioTipoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;

public interface EmpresaDomicilioService {

	List<EmpresaDomicilioBO> consultar(Integer id);
	List<EmpresaDomicilioBO> consultarTipoReales(Integer empresaId);
	List<DomicilioTipoBO> consultarTipos();
	
	EmpresaDomicilioBO guardar(Integer empresaId, EmpresaDomicilioBO domicilio);
	public void borrar(Integer empresaId, Integer domicilioId);
	
}
