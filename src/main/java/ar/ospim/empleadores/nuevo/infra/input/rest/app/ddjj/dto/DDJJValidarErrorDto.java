package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DDJJValidarErrorDto {
     
	 private String cuil;
     private String codigo; //campo del registro
	 private String descripcion; //`El campo '${key}' está vacío.`,
	 private String indice; //secuencial ???
	 
}
