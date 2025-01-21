package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AporteBO {
	private String codigo;
	private String descripcion;
	private String entidad;	
	private Integer orden;
	private Boolean ddjj;
}
