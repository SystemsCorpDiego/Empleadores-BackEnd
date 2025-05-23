package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;

@Mapper
public interface ConvenioMapper {
  
	@Mapping(target = "deuda", source = "importeDeuda")
	@Mapping(target = "interes", source = "importeIntereses")
	@Mapping(target = "saldoFavor", source = "importeSaldoFavor")
	@Mapping(target = "intencionPago", source = "intencionDePago")	
	@Mapping(target = "convenioNro", source = "convenioNumeroMolineros")	
	@Mapping(target = "empresaId", source = "empresa.id")
	@Mapping(target = "cuit", source = "empresa.cuit")
	@Mapping(target = "razonSocial", source = "empresa.razonSocial")	
	public ConvenioDto run(Convenio dto);
	
	
	public List<ConvenioActaDto> run(List<ConvenioActa> lst);	
	
	@Mapping(target = "capital", source = "acta.capital")
	@Mapping(target = "interes", source = "acta.interes")
	@Mapping(target = "actaId", source = "acta.id")
	@Mapping(target = "numero", source = "acta.numero")
	public ConvenioActaDto run(ConvenioActa dto);
	
	
	public List<ConvenioDDJJDto> run2(List<ConvenioDdjj> lst);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "ddjjId", source = "ddjj.id")
	@Mapping(target = "periodo", source = "ddjj.periodo")
	@Mapping(target = "secuencia", source = "ddjj.secuencia")
	public ConvenioDDJJDto run(ConvenioDdjj dto);
	
	
	public List<ConvenioAjusteDto> run3(List<ConvenioAjuste> lst);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "ajusteId", source = "ajuste.id")
	@Mapping(target = "vigencia", source = "ajuste.vigencia")
	@Mapping(target = "motivo", source = "ajuste.motivo")
	@Mapping(target = "importe", source = "importe")
	public ConvenioAjusteDto run(ConvenioAjuste dto);
	
	
	public List<ConvenioConsultaDto> run4(List<Convenio> lst);	
	
	@Mapping(target = "cuit", source = "empresa.cuit")
	@Mapping(target = "razonSocial", source = "empresa.razonSocial")
	@Mapping(target = "fecha", source = "intencionDePago")	
	@Mapping(target = "capital", source = "importeDeuda")
	@Mapping(target = "interes", source = "importeIntereses")
	@Mapping(target = "saldoFavor", source = "importeSaldoFavor")
	public ConvenioConsultaDto run2 (Convenio dto);
	
}
