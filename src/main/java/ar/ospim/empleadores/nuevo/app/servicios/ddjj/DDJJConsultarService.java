package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPeriodoInfoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesEmpresaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuencia;

public interface DDJJConsultarService {

	public DDJJPeriodoInfoDto consultarInfoPeriodo(Integer empresaId, LocalDate periodo);

	public DDJJBO consultarPeriodoAnterior(Integer empresaId, LocalDate periodo);
	
	public List<DDJJBoletaArmadoDetalleAfiliadoDto> empleadosPorAporte(Integer ddjjId, String aporteCodigo);
	
	public DDJJBO consultar(Integer id);
	
	public List<DDJJSecuencia> buscarSecuenciasPosterioresEnElPeriodo(Integer  empresaId, Integer ddjjId);
	
	public List<DDJJTotalesEmpresaDto> consultarTotales(DDJJConsultaFiltroDto filtro);	
	
	public Optional<DDJJBO> getUltimaPresentada(Integer empresaId, LocalDate periodo);
	
	public Optional<Integer> getUltimoIngresadoPeriodo(Integer id);
}
