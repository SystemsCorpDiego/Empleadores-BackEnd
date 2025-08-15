package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import lombok.Getter;

@Getter
public enum ConvenioEnumException {	
	CUOTA_CANTI_MENOR_A_CHEQUES,
	CUOTA_IMPORTE_MENOR_TOTAL_CHEQUES,
	ESTADO_PRESENTADA_IMPCUOTAS_DIF_IMPCHEQUES,
	ESTADO_PRESENTADA_FECHAPAGO_VENCIDA,
	ESTADO_PENDIENTE_EXISTENTE,
	ESTADO_CAMBIO_SIN_PRESENTAR,
	ESTADO_CAMBIO_PRESENTAR_SOLO_EMPLEADOR
	;
	
	public String getMsgKey() {
		return "convenio." + this.name();
	}
}
