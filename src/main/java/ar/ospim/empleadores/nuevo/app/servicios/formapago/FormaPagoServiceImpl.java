package ar.ospim.empleadores.nuevo.app.servicios.formapago;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.FormaPagoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FormaPagoServiceImpl implements FormaPagoService {
	/*
	private final String VENTANILLA = "VENTANILLA";
	private final String VENTANILLA_DESC = "Ventanilla";
	private final String REDLINK = "REDLINK";
	private final String REDLINK_DESC = "Red Link";
	private final String PMCUENTAS = "PMCUENTAS";
	private final String PMCUENTAS_DESC = "PagoMisCuentas";
	*/
	public List<FormaPagoBO> consultar() {
		List<FormaPagoBO> lst = new ArrayList<FormaPagoBO>();
		FormaPagoBO reg = new FormaPagoBO();
		reg.setCodigo(FormaPagoEnum.VENTANILLA.getCodigo()); 
		reg.setDescripcion(FormaPagoEnum.VENTANILLA.getDescripcion());
		lst.add(reg);
		
		reg = new FormaPagoBO();
		reg.setCodigo(FormaPagoEnum.REDLINK.getCodigo());
		reg.setDescripcion(FormaPagoEnum.REDLINK.getDescripcion());
		lst.add(reg);
		
		reg = new FormaPagoBO();
		reg.setCodigo(FormaPagoEnum.PMCUENTAS.getCodigo());
		reg.setDescripcion(FormaPagoEnum.PMCUENTAS.getDescripcion());
		lst.add(reg);
		
		return lst;
	}

	public FormaPagoBO get(String codigo) {
		FormaPagoBO reg = null;
		if ( codigo.equals(FormaPagoEnum.PMCUENTAS.getCodigo()) ) { 
			reg = new FormaPagoBO();
			reg.setCodigo(FormaPagoEnum.PMCUENTAS.getCodigo());
			reg.setDescripcion(FormaPagoEnum.PMCUENTAS.getDescripcion());
		}
		if ( codigo.equals(FormaPagoEnum.REDLINK.getCodigo()) ) { 
			reg = new FormaPagoBO();
			reg.setCodigo(FormaPagoEnum.REDLINK.getCodigo());
			reg.setDescripcion(FormaPagoEnum.REDLINK.getDescripcion());
		}
		if ( codigo.equals(FormaPagoEnum.VENTANILLA.getCodigo()) ) { 
			reg = new FormaPagoBO();
			reg.setCodigo(FormaPagoEnum.VENTANILLA.getCodigo());
			reg.setDescripcion(FormaPagoEnum.VENTANILLA.getDescripcion());
		}
		return reg;
	}
	
	public Boolean existe(String codigo) {
		if ( FormaPagoEnum.VENTANILLA.getCodigo().equals(codigo) ||  
				FormaPagoEnum.REDLINK.getCodigo().equals(codigo) || 
				FormaPagoEnum.PMCUENTAS.getCodigo().equals(codigo) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getFormaPagoCodigoVentanillaBanco() {
		return  FormaPagoEnum.VENTANILLA.getCodigo();
	}
	
	public Boolean esPagoMisCuentas(String codigo) {
		return FormaPagoEnum.PMCUENTAS.getCodigo().equals(codigo);
	}
	
	public Boolean esRedLink(String codigo) {
		return FormaPagoEnum.REDLINK.getCodigo().equals(codigo);
	}
	
	public Boolean generaVEP(String codigo) {
		if ( FormaPagoEnum.REDLINK.getCodigo().equals(codigo) || FormaPagoEnum.PMCUENTAS.getCodigo().equals(codigo)  ) {
			return true;
		} 
		return false;
	}
}
