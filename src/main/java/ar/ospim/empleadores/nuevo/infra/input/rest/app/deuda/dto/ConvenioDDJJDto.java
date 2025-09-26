package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioDDJJDto {
	private Integer id;					//convenio_ddjj.id					//convenio_periodo_detalle.id
	private Long ddjjId;				//ddjj.id								   //deuda_nomina_id
	private LocalDate periodo;		//ddjj.periodo						  // convenio_periodo_detalle.periodo
	private Integer secuencia;		//ddjj.secuencia					 // null
	
	private List<ConvenioDDJJDeudaNominaDto> deudaNominas;
}
