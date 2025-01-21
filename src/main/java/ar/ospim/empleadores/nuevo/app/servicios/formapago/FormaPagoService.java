package ar.ospim.empleadores.nuevo.app.servicios.formapago;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;

public interface FormaPagoService {
	public FormaPagoBO get(String codigo);
	public List<FormaPagoBO> consultar();
	public Boolean existe(String codigo);
	public String getFormaPagoCodigoVentanillaBanco();
	public Boolean generaVEP(String codigo);
}
