package ar.ospim.empleadores.nuevo.dominio;

import lombok.Data;

@Data
public class MailBO {
	String to;
	String titulo;
	String msg;
	
}
