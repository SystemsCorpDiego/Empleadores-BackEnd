package ar.ospim.empleadores.nuevo.dominio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailTipoDeudaInfoBO {

	private String cuit;
	private String email;
	private String entidad;
	private BigDecimal importe;
	private BigDecimal interes;	
	
}

