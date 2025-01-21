package ar.ospim.empleadores.comun.exception;

import java.util.Collections;
import java.util.Map;

import lombok.ToString;

//@JsonInclude(Include. NON_NULL)
@ToString
public class ApiErrorMessageDto {
	public final String tipo;
	public final String ticket;
	public final String codigo;
	public final String descripcion;
	public final Map<String, Object> args;

	public ApiErrorMessageDto(String tipo, String ticket, String code, String text, Map<String, Object> args) {
		this.codigo = code;
		this.descripcion = text;
		this.args = args;
		this.ticket = ticket;
		this.tipo = tipo;
	}

	public ApiErrorMessageDto(String tipo, String ticket, String code, String text) {
		this(tipo, ticket, code, text, Collections.emptyMap());
	}
	public ApiErrorMessageDto(String code, String text, Map<String, Object> args) {
		this(null, null, code, text, Collections.emptyMap());
	}

	public ApiErrorMessageDto(String code, String text) {
		this(code, text, Collections.emptyMap());
	}
	
	
}
