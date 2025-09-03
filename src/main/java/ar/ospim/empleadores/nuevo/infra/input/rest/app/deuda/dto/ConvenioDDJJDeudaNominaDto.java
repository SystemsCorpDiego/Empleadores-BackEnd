package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioDDJJDeudaNominaDto {
	private Integer id;									//convenio_ddjj_deuda_nomina.id						//convenio_periodo_detalle.id
	private Integer boletaId;							//convenio_ddjj_deuda_nomina.boleta_id			//convenio_periodo_detalle.boleta_id
	
	private String aporte;								//convenio_ddjj_deuda_nomina.aporte				//convenio_periodo_detalle.aporte
	private String aporteDescripcion;
	private BigDecimal importe;					//convenio_ddjj_deuda_nomina.importe				//convenio_periodo_detalle.aporte_importe
	private BigDecimal interes;					//convenio_ddjj_deuda_nomina.interes				//convenio_periodo_detalle.interes
	private LocalDate vencimiento;				////convenio_ddjj_deuda_nomina.vencimiento	// null
	
}
