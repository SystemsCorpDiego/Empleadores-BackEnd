package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioDetalleService {

	public Convenio runGrabar(Convenio convenio);
	
	public Convenio runActas(Convenio convenio, List<Integer> dto);
	public Convenio runActasGrabar(Convenio convenio);
	
	public Convenio runAjustes(Convenio convenio, List<Integer> dto);
	public Convenio runAjustesGrabar(Convenio convenio);
	
	public Convenio runPeriodos(Convenio convenio, List<String> dto);
	public Convenio runPeriodosGrabar(Convenio convenio);
	
}
