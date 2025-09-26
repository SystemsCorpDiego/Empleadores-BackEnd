package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioAltaDto {
	
	private Integer empresaId;	
	private String entidad;
	
	private Integer cantidadCuota;
	private LocalDate fechaPago;
	private String medioDePago;
	
	private List<Integer> actas;
	private List<String> ddjjs; // viene periodo+aporte 			//old: deuda_nomina.id
	private List<Integer> ajustes;
	
}
