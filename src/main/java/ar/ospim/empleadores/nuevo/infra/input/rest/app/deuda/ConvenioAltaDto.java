package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioAltaDto {
	
	private Integer empresaId;
	
	private String entidad;
	private Integer cantidadCuota;
	private LocalDate fechaPago;
	
	private List<Integer> actas;
	private List<Integer> ddjjs;
	private List<Integer> ajustes;
	
}
