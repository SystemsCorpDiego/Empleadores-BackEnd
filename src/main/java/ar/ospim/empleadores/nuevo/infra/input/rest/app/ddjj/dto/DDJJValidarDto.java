package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJValidarDto {
	private List<DDJJValidarErrorDto> errores;
}
