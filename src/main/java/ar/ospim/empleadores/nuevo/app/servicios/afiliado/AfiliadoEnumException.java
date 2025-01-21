package ar.ospim.empleadores.nuevo.app.servicios.afiliado;

import lombok.Getter;

@Getter
public enum AfiliadoEnumException {
	CUIL_ALTA_EXISTENTE,
	CUIL_OBLIGATORIO,
	NOMBRES_OBLIGATORIO,
	;
	
	public String getMsgKey() {
		return "afiliado." + this.name();
	}

}
