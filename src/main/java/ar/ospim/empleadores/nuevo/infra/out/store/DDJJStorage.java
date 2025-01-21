package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuencia;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotales;

public interface DDJJStorage {

	public  Optional<DDJJBO> findById(Integer id);
	public DDJJBO guardar(DDJJBO reg);
	public void borrar(Integer ddjjId);
	
	public Optional<Integer> getUltimoPresentado(Integer empresaId, LocalDate periodo);
	public Optional<Integer> getUltimoIngresadoPeriodo(Integer id);
	public Optional<Integer> getUltimoIngresadoPeriodo(Integer empresaId, LocalDate periodo);
	public Integer getSecuencia(Integer ddjjId, LocalDate periodo);
	public void presentar(Integer ddjjId, Integer secuencia);
	public void setEstado(Integer ddjjId, String estado);
	
	//public DDJJBO actualizar(DDJJBO reg);		
	public List<DDJJTotales> consultarTotales(Integer empresaId);
	public List<DDJJTotales> consultarTotales(String cuit, LocalDate desde, LocalDate hasta);
	public List<DDJJTotales> consultarTotales(Integer empresaId, LocalDate desde, LocalDate hasta);
	public List<DDJJBoletaArmadoDetalleAfiliadoDto> empleadosPorAporte(Integer ddjjId, String aporteCodigo);
	
	public List<DDJJBO> findByEmpresaIdAndPeriodo(Integer empresaId, LocalDate periodo);
	public  Optional<LocalDate> findUltimoPeriodoPresentada(Integer empresaId);
	public List<Integer> findPeriodoPendiente(Integer EmpresaId, LocalDate periodo);
	
	public List<DDJJSecuencia> consultarSecuenciasPosterioresEnElPeriodo(Integer empresaId, Integer ddjjId);
	
}
