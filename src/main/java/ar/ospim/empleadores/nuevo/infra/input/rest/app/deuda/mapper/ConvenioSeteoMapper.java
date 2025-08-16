package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioSeteoCuitDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioSeteoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.FormaPagoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;

@Mapper(imports = {Arrays.class})
public interface ConvenioSeteoMapper {

	List<ConvenioSeteoDto> run(List<ConvenioSeteo> reg);
	ConvenioSeteoDto run(ConvenioSeteo reg);
	ConvenioSeteo run(ConvenioSeteoDto reg);

	@Mapping(target = "mediosDePago", expression = "java( mapPropertiesToArray(dto) )")
	@Mapping(target = "cuotas", source = "cuotas")
	@Mapping(target = "diasIntencion", source = "intencionPagoDiasMax")
	ConvenioSeteoCuitDto run2(ConvenioSeteo dto);
	
	
	
	default String[] mapPropertiesToArray(ConvenioSeteo dto) {
		
		List<String> list = new ArrayList<String>();
		
		if ( dto.getFormaPagoVentanilla() )
			list.add( FormaPagoEnum.VENTANILLA.getCodigo() );
		
		if ( dto.getFormaPagoRedlink() ) 
			list.add(FormaPagoEnum.REDLINK.getCodigo());
		
		if ( dto.getFormaPagoBanelco() ) 
			list.add(FormaPagoEnum.BANELCO.getCodigo());

		if ( dto.getFormaPagoCheque() ) 
			list.add(FormaPagoEnum.CHEQUE.getCodigo() );

		return list.toArray(new String[0]);
    }
}
