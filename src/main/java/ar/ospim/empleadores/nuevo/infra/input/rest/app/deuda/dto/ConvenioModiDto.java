package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioModiDto {
	private Integer convenioId;
	private Integer empresaId;
	
	private Integer cantidadCuota;
	private LocalDate fechaPago;
	
	private List<Integer> actas;
	private List<Integer> ddjjs;
	private List<Integer> ajustes;
	

}
