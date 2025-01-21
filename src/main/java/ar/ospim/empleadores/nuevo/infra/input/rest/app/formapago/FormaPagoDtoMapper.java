package ar.ospim.empleadores.nuevo.infra.input.rest.app.formapago;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;

@Mapper
public interface FormaPagoDtoMapper {
	FormaPagoBO map(FormaPagoDto feriado);
	FormaPagoDto map(FormaPagoBO feriado);
 
	List<FormaPagoDto> map(List<FormaPagoBO> feriados);
}
