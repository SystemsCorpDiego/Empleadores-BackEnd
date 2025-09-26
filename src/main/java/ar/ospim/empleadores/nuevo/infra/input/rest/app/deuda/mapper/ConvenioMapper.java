package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioActaDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioActaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAjusteDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAjusteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;

@Mapper
public interface ConvenioMapper {
  		
	@Mapping(target = "intencionPago", source = "fechaPago")
	@Mapping(target = "medioPago", source = "medioDePago")	
	PlanPagoDto run2 (ConvenioModiDto dto);
	
	@Mapping(target = "cantidadCuota", source = "cantidadCuota")
	ConvenioAltaDto run(ConvenioModiDto dto);
	
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
	
	

	
	public List<ConvenioAjusteDto> run3(List<ConvenioAjuste> lst);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "ajusteId", source = "ajuste.id")
	@Mapping(target = "vigencia", source = "ajuste.vigencia")
	@Mapping(target = "motivo", source = "ajuste.motivo")
	@Mapping(target = "importe", source = "importe")
	public ConvenioAjusteDto run(ConvenioAjuste dto);
	
	
	public List<ConvenioConsultaDto> run4(List<Convenio> lst);	
	
	@Mapping(target = "empresaId", source = "empresa.id")
	@Mapping(target = "cuit", source = "empresa.cuit")
	@Mapping(target = "razonSocial", source = "empresa.razonSocial")
	@Mapping(target = "fecha", source = "createdOn")	
	@Mapping(target = "capital", source = "importeDeuda")
	@Mapping(target = "interes", source = "importeIntereses")
	@Mapping(target = "saldoFavor", source = "importeSaldoFavor")
	@Mapping(target = "numero", source = "convenioNumeroMolineros")	 
	@Mapping(target = "cuotas", source = "cuotasCanti")
	public ConvenioConsultaDto run2 (Convenio dto);
	
	
	@Mapping(target = "numero", source = "cuotaNro")
	public ConvenioCuotaDto run2(ConvenioCuota convenioCuota);
	
	public List<ConvenioCuotaConsultaDto> run6 ( List<ConvenioCuota> lst);
		
	//@Mapping(target = "importe", source = "importe")
	@Mapping(target = "id", source = "id")
	@Mapping(target = "numero", source = "cuotaNro")
	@Mapping(target = "importe", source ="importeTotalCuota")	
	@Mapping(target = "vencimiento", source = "vencimiento")
	public ConvenioCuotaConsultaDto run ( ConvenioCuota cuota );

	
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
	@Mapping(target = "lstCuotas", source = "cuotas")
	ConvenioDeudaDto run3( Convenio dto);
	
	 
	@Mapping(target = "convenioAjusteId", source = "id")
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
	
	List<ConvenioActaDeudaDto> run8 ( List<ActaMolineros> dto);
	
	@Mapping(target = "nroActa", source = "numero") 	
	@Mapping(target = "estadoDeuda", source = "estado") 	
	@Mapping(target = "fechaActa", source = "fecha") 	
	@Mapping(target = "importe", source = "capital")
	@Mapping(target = "intereses", source = "interes")
	ConvenioActaDeudaDto run8 ( ActaMolineros dto);
		
	
	List<ConvenioAjusteDeudaDto> run9 ( List<IGestionDeudaAjustesDto> dto );
	ConvenioAjusteDeudaDto run9 ( IGestionDeudaAjustesDto dto );
	
	List<ConvenioDDJJDeudaDto> run10(List<IGestionDeudaDDJJDto> dto);
	ConvenioDDJJDeudaDto run10(IGestionDeudaDDJJDto dto);
	
	@Mapping(target = "id", source = "idString")
	ConvenioDDJJDeudaDto runCastIdString(ConvenioDDJJDeudaDto reg);
	List<ConvenioDDJJDeudaDto> runCastIdString(List<ConvenioDDJJDeudaDto> lst);

}
