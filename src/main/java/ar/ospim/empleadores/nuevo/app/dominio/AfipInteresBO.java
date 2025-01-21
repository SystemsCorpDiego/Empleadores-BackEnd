package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AfipInteresBO {
    private Integer id;

	private LocalDate desde;
	private LocalDate hasta;
	
	private BigDecimal indice;

}
