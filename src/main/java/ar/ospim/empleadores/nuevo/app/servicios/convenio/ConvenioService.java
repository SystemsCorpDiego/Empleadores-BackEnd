package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioConsultaFiltro;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;

public interface ConvenioService {

	public Convenio generar(ConvenioAltaDto dto);
	public Convenio cambiarEstado(Integer empresaId, Integer convenioId, String estado);
	
	public Convenio get(Integer empresaId, Integer convenioId);	
	public List<Convenio> get(ConvenioConsultaFiltro filtro);
	
	public List<ConvenioCuotaConsultaDto> getCuotas(Integer empresaId, Integer convenioId);
	public List<ConvenioCuotaCheque> getCheques(Integer empresaId, Integer convenioId, Integer cuotaId);
	public ConvenioCuotaCheque getCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId);
	public void borrarCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId);
	
	public ConvenioCuotaCheque generar(ConvenioCuotaChequeAltaDto cheque);
	public ConvenioCuotaCheque actualizar(ConvenioCuotaChequeAltaDto cheque);
}
