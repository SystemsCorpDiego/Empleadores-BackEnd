package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.util.List;

import lombok.Data;

@Data
public class GestionDeudaDto {

	List<GestionDeudaDDJJDto> declaracionesJuradas;
	List<GestionDeudaActaDto> actas;
	List<GestionDeudaAjustesDto> saldosAFavor;
	
}
