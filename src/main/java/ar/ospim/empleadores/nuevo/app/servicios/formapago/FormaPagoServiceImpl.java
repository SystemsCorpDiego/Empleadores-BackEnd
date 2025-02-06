package ar.ospim.empleadores.nuevo.app.servicios.formapago;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FormaPagoServiceImpl implements FormaPagoService {
	
	private final String VENTANILLA = "VENTANILLA";
	private final String VENTANILLA_DESC = "Ventanilla";
	private final String REDLINK = "REDLINK";
	private final String REDLINK_DESC = "Red Link";
	private final String PMCUENTAS = "PMCUENTAS";
	private final String PMCUENTAS_DESC = "PagoMisCuentas";
	
	public List<FormaPagoBO> consultar() {
		List<FormaPagoBO> lst = new ArrayList<FormaPagoBO>();
		FormaPagoBO reg = new FormaPagoBO();
		reg.setCodigo(VENTANILLA);
		reg.setDescripcion(VENTANILLA_DESC);
		lst.add(reg);
		
		reg = new FormaPagoBO();
		reg.setCodigo(REDLINK);
		reg.setDescripcion(REDLINK_DESC);
		lst.add(reg);
		
		reg = new FormaPagoBO();
		reg.setCodigo(PMCUENTAS);
		reg.setDescripcion(PMCUENTAS_DESC);
		lst.add(reg);
		
		return lst;
	}

	public FormaPagoBO get(String codigo) {
		FormaPagoBO reg = null;
		if ( codigo.equals(PMCUENTAS) ) { 
			reg = new FormaPagoBO();
			reg.setCodigo(PMCUENTAS);
			reg.setDescripcion(PMCUENTAS_DESC);
		}
		if ( codigo.equals(REDLINK) ) { 
			reg = new FormaPagoBO();
			reg.setCodigo(REDLINK);
			reg.setDescripcion(REDLINK_DESC);
		}
		if ( codigo.equals(VENTANILLA) ) { 
			reg = new FormaPagoBO();
			reg.setCodigo(VENTANILLA);
			reg.setDescripcion(VENTANILLA_DESC);
		}
		return reg;
	}
	
	public Boolean existe(String codigo) {
		if ( VENTANILLA.equals(codigo) ||  REDLINK.equals(codigo) || PMCUENTAS.equals(codigo) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getFormaPagoCodigoVentanillaBanco() {
		return  VENTANILLA;
	}
	
	public Boolean generaVEP(String codigo) {
		if ( REDLINK.equals(codigo) || PMCUENTAS.equals(codigo)  ) {
			return true;
		} 
		return false;
	}
}
