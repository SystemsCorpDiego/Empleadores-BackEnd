package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoTipoBO;

public interface EmpresaContactoService {

	List<ContactoBO> consultar(Integer empresaId);	
	List<ContactoTipoBO> consultarTipos();

	ContactoBO guardar(Integer empresaId, ContactoBO domicilio);
	public void borrar(Integer empresaId, Integer domicilioId);
	
}
