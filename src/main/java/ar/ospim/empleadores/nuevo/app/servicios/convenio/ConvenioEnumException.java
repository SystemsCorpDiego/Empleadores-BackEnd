package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import lombok.Getter;

@Getter
public enum ConvenioEnumException {	
	CUOTA_CANTI_MENOR_A_CHEQUES,
	;
	
	public String getMsgKey() {
		return "convenio." + this.name();
	}
}
