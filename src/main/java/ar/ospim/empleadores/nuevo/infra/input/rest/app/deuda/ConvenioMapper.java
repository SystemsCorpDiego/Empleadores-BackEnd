package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;

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
	@Mapping(target = "cuotas", source = "cuotasCanti")
	public ConvenioDto run(Convenio dto);
	
	
	public List<ConvenioActaDto> run(List<ConvenioActa> lst);	
	
	@Mapping(target = "capital", source = "acta.capital")
	@Mapping(target = "interes", source = "acta.interes")
	@Mapping(target = "actaId", source = "acta.id")
	@Mapping(target = "numero", source = "acta.numero")
	@Mapping(target = "estado", source = "acta.estado")
	@Mapping(target = "fecha", source = "acta.fecha")
	public ConvenioActaDto run(ConvenioActa dto);
	
	
	public List<ConvenioDDJJDto> run2(List<ConvenioDdjj> lst);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "ddjjId", source = "ddjj.id")
	@Mapping(target = "periodo", source = "ddjj.periodo")
	@Mapping(target = "secuencia", source = "ddjj.secuencia")
	@Mapping(target = "deudaNominas", source = "ddjjDeudaNomina")	 
	public ConvenioDDJJDto run(ConvenioDdjj dto);
	

	public List<ConvenioDDJJDeudaNominaDto> run5(List<ConvenioDdjjDeudaNomina> lst);
	
	@Mapping(target = "importe", source = "aporteImporte")	 
	public ConvenioDDJJDeudaNominaDto run(ConvenioDdjjDeudaNomina dto);
	
	
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
	@Mapping(target = "numero", source = "convenioNumeroMolineros")	 
	@Mapping(target = "cuotas", source = "cuotasCanti")
	public ConvenioConsultaDto run2 (Convenio dto);
	
	
	public List<ConvenioCuotaConsultaDto> run6 ( List<ConvenioCuota> lst);
		
	@Mapping(target = "id", source = "id")
	@Mapping(target = "numero", source = "cuotaNro")
	@Mapping(target = "importe", source = "importe")
	@Mapping(target = "vencimiento", source = "vencimiento")
	//@Mapping(target = "chequesNro", source = "")
	//@Mapping(target = "Chequestotal", source = "")	
	public ConvenioCuotaConsultaDto run ( ConvenioCuota lst);

	@Mapping(target = "id", source = "id") 
	public ConvenioCuotaChequeDto run(ConvenioCuotaCheque dto);

	
	List<ConvenioCuotaChequeConsultaDto> run7 (List<ConvenioCuotaCheque> lst);
	
	ConvenioCuotaChequeConsultaDto run2(ConvenioCuotaCheque dto);
	
	@Mapping(target = "deuda", source = "importeDeuda")
	@Mapping(target = "interes", source = "importeIntereses")
	@Mapping(target = "saldoFavor", source = "importeSaldoFavor")
	@Mapping(target = "intencionPago", source = "intencionDePago")	
	@Mapping(target = "convenioNro", source = "convenioNumeroMolineros")	
	@Mapping(target = "empresaId", source = "empresa.id")
	@Mapping(target = "cuit", source = "empresa.cuit")
	@Mapping(target = "razonSocial", source = "empresa.razonSocial")
	@Mapping(target = "cuotas", source = "cuotasCanti")
	@Mapping(target = "declaracionesJuradas", ignore = true)
	@Mapping(target = "saldosAFavor", source = "ajustes")
	ConvenioDeudaDto run3( Convenio dto);
	
	 
	@Mapping(target = "convenioAjusteid", source = "id")
	@Mapping(target = "motivo", source = "ajuste.motivo")
	@Mapping(target = "importe", source = "importe")
	@Mapping(target = "id", source = "ajuste.id")
	@Mapping(target = "vigencia", source = "ajuste.vigencia")
	ConvenioAjusteDeudaDto run2(ConvenioAjuste dto);
			
	@Mapping(target = "convenioActaId", source = "id")
	@Mapping(target = "id", source = "acta.id")
	@Mapping(target = "nroActa", source = "acta.numero")
	@Mapping(target = "estadoDeuda", source = "acta.estado")
	@Mapping(target = "fechaActa", source = "acta.fecha")
	@Mapping(target = "importe", source = "acta.capital")
	@Mapping(target = "intereses", source = "acta.interes")	
	ConvenioActaDeudaDto convenioActaToConvenioActaDeudaDto(ConvenioActa convenioActa);
	
}
