package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import lombok.Getter;

@Getter
public enum BoletaPagoEnumException {
	DDJJ,
	DDJJ_NO,
	REGISTRO_BAJA,
	APORTE_EN_ACTA,
	INTERES_EN_ACTA,
	VTO_EN_ACTA,
	ATRIBUTO_OBLIGATORIO,
	AJUSTE_ASIGNADO,
	APORTE_DDJJ_INEXISTENTE,
	APORTE_INFO_PAGO_INEXISTENTE,
	DDJJ_CON_BOLETAS,
	AFIP_INTERES_INEXISTENTE,
	FORMA_PAGO_INEXISTENTE,
	FORMA_PAGO_APORTE_INEXISTENTE,
	DDJJ_NUEVA,
	DDJJ_NUEVA_PENDIENTE,
	VTO_SETEO,
    ;
	
	public String getMsgKey() {
		return "boleta." + this.name();
	}

}
