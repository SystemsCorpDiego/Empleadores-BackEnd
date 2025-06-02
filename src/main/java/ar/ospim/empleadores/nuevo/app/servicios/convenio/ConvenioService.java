package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioConsultaFiltro;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;

public interface ConvenioService {

	public Convenio generar(ConvenioAltaDto dto);
	public Convenio cambiarEstado(Integer empresaId, Integer convenioId, String estado);
	
	public Convenio get(Integer empresaId, Integer convenioId);	
	public List<Convenio> get(ConvenioConsultaFiltro filtro);
	
	public BigDecimal calcularImporteCuota(BigDecimal capital, Integer cuotas, LocalDate vencimiento );
	
	public List<ConvenioCuotaConsultaDto> getCuotas(Integer empresaId, Integer convenioId);
	public List<ConvenioCuotaCheque> getCheques(Integer empresaId, Integer convenioId, Integer cuotaId);
	public ConvenioCuotaCheque getCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId);
	public void borrarCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId);
	
	public ConvenioCuotaCheque generar(ConvenioCuotaChequeAltaDto cheque);
	public ConvenioCuotaCheque actualizar(ConvenioCuotaChequeAltaDto cheque);
	
	
	public  void borrarActa(Integer empresaId, Integer convenioId, Integer actaId);
	public  ConvenioActa asignarActa(Integer empresaId, Integer convenioId, Integer actaId);
	
	public  void borrarAjuste(Integer empresaId, Integer convenioId, Integer ajusteId);
	public  ConvenioAjuste asignarAjuste(Integer empresaId, Integer convenioId, Integer ajusteId);
}
