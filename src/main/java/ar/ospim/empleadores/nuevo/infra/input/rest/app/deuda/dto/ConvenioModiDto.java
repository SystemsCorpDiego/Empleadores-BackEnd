package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioModiDto {	
	
	private Integer empresaId;
	private Integer convenioId;
	
	private Integer cantidadCuota;
	private LocalDate fechaPago;
	private String medioDePago;
	
	private List<Integer> actas;
	private List<Integer> ddjjs;  //viene deuda_nomina.id
	private List<Integer> ajustes;
	

}
