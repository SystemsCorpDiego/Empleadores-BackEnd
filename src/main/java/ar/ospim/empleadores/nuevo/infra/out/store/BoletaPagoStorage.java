package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;

public interface BoletaPagoStorage {

	public  Optional<BoletaPagoBO> findById(Integer id);
	public  List<BoletaPagoBO> findByDDJJId(Integer ddjjId);
	public BoletaPagoBO guardar(BoletaPagoBO reg);
	public void borrar(Integer id);
	
	public  List<BoletaPagoBO> findByEmpresaIdSinDDJJ(Integer empresaId);
	public  List<BoletaPagoBO> findByEmpresaIdConDDJJ(Integer empresaId);
	
	public  List<BoletaPagoBO> findByEmpresaIdSinDDJJ(Integer empresaId, LocalDate desde, LocalDate hasta);
	
	public List<BoletaPagoDDJJConsulta> consultarConDDJJ(BoletaPagoFiltroDto filtro);
	 
	public BoletaPagoDDJJConsulta consultarConDDJJ(Integer boletaId);
	public List<BoletaPagoDDJJConsulta> consultarByDdjjId(Integer ddjjId);
	
	public Boolean existeBoletaConAjuste(Integer ajusteId);
	public Boolean existeBoletaParaDDJJ(Integer ddjjId);
	public Integer getSecuenciaProx(Integer empresaId);
	
	public void registrarImpresion(Integer boletaId);
	public void registrarBaja(Integer boletaId);
	
	public List<Integer> findByEmpresaIdAndPeriodoImpaga(Integer empresaId, LocalDate periodo);
}
