package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoAjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAjusteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;

public interface BoletaPagoCalcularAjustesService {
	
	@Transactional
	public List<DDJJBoletaArmadoDetalleAjusteDto> run(DDJJBoletaArmadoDetalleDto boleta, Integer empresaId);
	
	public List<BoletaPagoAjusteBO> run(BoletaPagoBO boleta, LocalDate periodoDDJJ);
	
	
}
