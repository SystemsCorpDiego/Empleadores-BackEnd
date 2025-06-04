package ar.ospim.empleadores.nuevo.infra.out.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioStorage {

	public Convenio getById(Integer id);
	public Convenio guardar(Convenio reg);	
	public Convenio get(Integer id);
	public List<Convenio> get(ConvenioConsultaFiltroDto filtro);
	
	public void actualizarImportes( Integer convenioId, BigDecimal p_imp_deuda, BigDecimal p_imp_interes, BigDecimal p_imp_saldo_favor );
	public void actualizarModoPago( Integer convenioId, Integer p_cuotas_canti, LocalDate p_intencion_pago );
	
}
