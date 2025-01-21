package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;

public interface BoletaPagoConsultaService {

	public List<BoletaPagoDDJJConsulta> runConDDJJ(BoletaPagoFiltroDto filtro);	
	
	public List<BoletaPagoDDJJConsulta> runByDdjjId(Integer ddjjId);
	public BoletaPagoDDJJConsulta runConDDJJ(Integer boletaId) ;
	public List<BoletaPagoBO> runSinDDJJ(Integer empresaId, LocalDate periodoD, LocalDate periodoH) ;
	
	public BoletaPagoBO find(Integer id);
	
	public Optional<BoletaPagoBO> findByDdjjIdAndAporte(Integer ddjjId, String aporte);
	
	public Integer getSecuenciaProx(Integer empresaId);

}
