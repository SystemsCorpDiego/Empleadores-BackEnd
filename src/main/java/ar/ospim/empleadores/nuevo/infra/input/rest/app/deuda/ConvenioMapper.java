package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;

@Mapper
public interface ConvenioMapper {
  
	@Mapping(target = "deuda", source = "importeDeuda")
	@Mapping(target = "interes", source = "importeIntereses")
	@Mapping(target = "saldoFavor", source = "importeSaldoFavor")
	@Mapping(target = "intencionPago", source = "intencionDePago")	
	@Mapping(target = "convenioNro", source = "convenioNumeroMolineros")
	//ddjjs
	public ConvenioAltaResponseDto run(Convenio dto);
	
	
	public List<ConvenioActaDto> run(List<ConvenioActa> lst);	
	
	@Mapping(target = "capital", source = "acta.capital")
	@Mapping(target = "interes", source = "acta.interes")
	@Mapping(target = "actaId", source = "acta.id")
	@Mapping(target = "numero", source = "acta.numero")
	public ConvenioActaDto run(ConvenioActa dto);
	
	
	public List<ConvenioDDJJDto> run2(List<ConvenioDdjj> lst);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "ddjjId", source = "ddjj.id")
	public ConvenioDDJJDto run(ConvenioDdjj dto);
	
	
}
