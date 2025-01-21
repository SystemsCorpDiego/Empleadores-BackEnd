package ar.ospim.empleadores.nuevo.app.dominio;

import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaDomicilioTipoEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class EmpresaDomicilioBO extends DomicilioBO {

	private EmpresaDomicilioTipoEnum tipo;
	private String planta;
	private String oficina;

	public void setTipo(String valor) {
		if ( valor != null) {
			switch (valor) {
	        case "REAL":
	            this.tipo = EmpresaDomicilioTipoEnum.REAL;
	            break;
	        case "FISCAL":
	            this.tipo = EmpresaDomicilioTipoEnum.FISCAL;
	            break;
	        default:
	            this.tipo = EmpresaDomicilioTipoEnum.REAL;
	        }		
		}
	}

	public boolean esDomicilioFiscal() {
		return tipo.equals(EmpresaDomicilioTipoEnum.FISCAL);
	}
	
}
