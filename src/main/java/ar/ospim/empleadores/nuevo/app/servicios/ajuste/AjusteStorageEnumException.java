package ar.ospim.empleadores.nuevo.app.servicios.ajuste;

import lombok.Getter;

@Getter
public enum AjusteStorageEnumException {
	AJUSTE_CON_BOLETA
    ;
	
	public String getMsgKey() {
		return "ajuste." + this.name();
	}

}
