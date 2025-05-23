package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioDto {

	private Integer id;
	private String entidad;
	
	private String empresaId;
	private String cuit;
	private String razonSocial;
	
	private BigDecimal deuda;
	private BigDecimal interes;
	private BigDecimal saldoFavor;
	private LocalDate intencionPago;
	private Integer cuotas;
	private String medioPago;
	private String convenioNro;
	
	private List<ConvenioActaDto> actas;
	private List<ConvenioDDJJDto> ddjjs;
	private List<ConvenioAjusteDto> ajustes;
	
}
