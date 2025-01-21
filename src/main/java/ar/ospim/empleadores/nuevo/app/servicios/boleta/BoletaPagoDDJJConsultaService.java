package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;

public interface BoletaPagoDDJJConsultaService {

	public DDJJBoletaArmadoDetalleDto run(Integer boletaId);
}
