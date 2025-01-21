package ar.ospim.empleadores.nuevo.app.servicios.feriado;

import lombok.Getter;

@Getter
public enum FeriadoStorageEnumException {
	ANIO_ORIGEN_FUTURO
    ;
	
	public String getMsgKey() {
		return "feriado." + this.name();
	}
}
