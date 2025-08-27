package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;

public interface ConvenioService {

	public Convenio generar(ConvenioAltaDto dto);
	public Convenio actualizar(ConvenioModiDto dto);
	public Convenio cambiarEstado(Integer empresaId, Integer convenioId, String estado);
	public Convenio cambiarEstado(Integer convenioId, String estado);
	public Convenio actualizarPlanPago(Integer empresaId, Integer convenioId, PlanPagoDto planPago);
	
	public ConvenioDeudaDto getConvenioDeudaDto(Convenio convenio);	
	
	public Convenio get(Integer empresaId, Integer convenioId);	
	public List<Convenio> get(ConvenioConsultaFiltroDto filtro);
	
	//public BigDecimal calcularImporteCuota(BigDecimal capital, Integer cuotas, LocalDate vencimiento );
	public List<CalcularCuotasCalculadaDto> calcularCuotas(Integer empresaId, BigDecimal capital, Integer cuotas, LocalDate vencimiento );
	
	public List<ConvenioCuotaConsultaDto> getCuotas(Integer empresaId, Integer convenioId);
	public List<ConvenioCuotaCheque> getCheques(Integer empresaId, Integer convenioId, Integer cuotaId);
	public ConvenioCuotaCheque getCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId);
	public void borrarCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId);
	
	public ConvenioCuotaCheque generar(ConvenioCuotaChequeAltaDto cheque);
	public ConvenioCuotaCheque actualizarCuotaCheque(ConvenioCuotaChequeAltaDto cheque);
	
	
	public  void borrarActa(Integer empresaId, Integer convenioId, Integer actaId);
	public  ConvenioActa asignarActa(Integer empresaId, Integer convenioId, Integer actaId);
	
	public  void borrarAjuste(Integer empresaId, Integer convenioId, Integer ajusteId);
	public  ConvenioAjuste asignarAjuste(Integer empresaId, Integer convenioId, Integer ajusteId);

	public  void borrarDDJJ(Integer empresaId, Integer convenioId, Integer ddjjId);
	public ConvenioDdjj asignarDDJJ(Integer empresaId, Integer convenioId, Integer ddjjId);
	
}
