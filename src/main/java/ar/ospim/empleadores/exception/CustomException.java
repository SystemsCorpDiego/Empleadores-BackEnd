package ar.ospim.empleadores.exception;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class CustomException  {
 
	private static final long serialVersionUID = -6671077499908766241L;
	
	private String nroTicket;
	private Long  codigo;
	private String descripcion;
	private Map<String, String> errores = new HashMap<>();

	public CustomException(Long  codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	 
	
}
