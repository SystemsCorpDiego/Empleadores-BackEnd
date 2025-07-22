package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IDeudaNominaDescargaDto {

	Integer getId();
	String getCuit();
	String getPeriodo();
	String getEntidad();
	String getAporte();
	String getAporteDescripcion();
	BigDecimal getAporte_importe();
	String  getVencimiento();
	BigDecimal getInteres();
	BigDecimal getAporte_pago();
	String getAporte_pago_fecha_info();
	Integer  getDdjj_id();
	Integer  getDdjj_secuencia();
	Integer  getBoleta_id();
	Integer  getBoletaPago_secuencia();
	Integer  getActa_id();
	Integer  getConvenio_id();
		 
}
