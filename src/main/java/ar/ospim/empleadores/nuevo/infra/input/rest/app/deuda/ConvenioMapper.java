package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

@Mapper
public interface ConvenioMapper {
  
	@Mapping(target = "deuda", source = "importeDeuda")
	@Mapping(target = "interes", source = "importeIntereses")
	@Mapping(target = "saldoFavor", source = "importeSaldoFavor")
	@Mapping(target = "intencionPago", source = "intencionDePago")	
	@Mapping(target = "convenioNro", source = "convenioNumeroMolineros")	
	public ConvenioAltaResponseDto run(Convenio dto);
	
}
