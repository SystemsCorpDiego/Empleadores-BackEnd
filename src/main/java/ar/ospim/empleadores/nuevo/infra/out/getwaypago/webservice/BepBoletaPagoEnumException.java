package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice;

import lombok.Getter;

@Getter
public enum BepBoletaPagoEnumException {
	BOLETA_NULL,
	REDLINK_ENTIDAD,
	REDLINK_APORTE,
	REDLINK_CUIT_PAGADOR,
	REDLINK_BOLETA_SIN_DDJJ,
	BOLETA_APORTE_NULL,
	BOLETA_DDJJ_INEXISTENTE,
    ;
	
	public String getMsgKey() {
		return "bep." + this.name();
	}
}
