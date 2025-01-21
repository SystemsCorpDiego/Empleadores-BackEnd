package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscalaSalarialBO {
	private Integer id;
	private String tipo; //PJ-Paritaria por Jornal, PS-Paritaria por Salario
	private String camara;
	private String categoria;
	private Integer antiguedad;
	private LocalDate vigencia;
	private BigDecimal basico;
}
