package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class GestionDeudaAjustesDto {

	Integer id;
	LocalDate vigencia;
	String motivo;
	BigDecimal importe;

}
