package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import lombok.Getter;

@Getter
public enum ConvenioEnumException {	
	CUOTA_CANTI_MENOR_A_CHEQUES,
	CUOTA_IMPORTE_MENOR_TOTAL_CHEQUES,
	ESTADO_PRESENTADA_IMPCUOTAS_DIF_IMPCHEQUES,
	;
	
	public String getMsgKey() {
		return "convenio." + this.name();
	}
}
