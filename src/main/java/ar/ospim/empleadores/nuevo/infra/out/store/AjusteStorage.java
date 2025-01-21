package ar.ospim.empleadores.nuevo.infra.out.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;

public interface AjusteStorage {	
	
	public List<AjusteBO> findAll();   
	public List<AjusteBO> findAllCrud();
	public AjusteBO save(AjusteBO aporte);	
	public void deleteById(Integer id);	
	public AjusteBO findById(Integer id);	
	public List<AjusteBO> consultarAportesVigentes(Integer empresaId, String aporte, LocalDate vigencia);
	public BigDecimal getImporteUsado(Integer aporteId);
	public void generarAjusteAutomaticoIPF(String p_aporte, Integer p_empresa_id);
}
