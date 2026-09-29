package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioCuotasCalcular {
	
	public Convenio run(Convenio convenio, PlanPagoDto  planPago);  
	public Convenio run(Convenio convenio);
	public Convenio grabar(Convenio convenio);
 
	public List<CalcularCuotasCalculadaDto> run(Integer empresaId, BigDecimal capital, Integer cuotas, LocalDate vencimiento );
	public List<CalcularCuotasCalculadaDto> run(String cuit, BigDecimal capital, Integer cuotas, LocalDate vencimiento ) ;
}
