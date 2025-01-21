package ar.ospim.empleadores.nuevo.app.servicios.ajuste;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.CodigoDescripDto;

public interface AjusteService {

	public List<AjusteBO> consultar();   

	public List<AjusteBO> consultarCrud();
	
	public AjusteBO guardar(AjusteBO reg);	
	
	public void borrar(Integer id);	
	
	public List<AjusteBO> consultarAportesVigentes(Integer empresaId, String aporte, LocalDate vigencia);
	
	public BigDecimal getImporteUsado(Integer ajusteId);
	
	public void generarAjusteAutomaticoIPF(String p_aporte, Integer p_empresa_id);

	public List<CodigoDescripDto> getMotivos();

	public String getMotivoDescripcion(String codigo);
}
