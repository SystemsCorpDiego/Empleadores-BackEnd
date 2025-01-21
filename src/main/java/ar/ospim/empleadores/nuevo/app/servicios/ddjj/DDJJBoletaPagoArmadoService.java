package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import java.time.LocalDate;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDto;

public interface DDJJBoletaPagoArmadoService {

	public DDJJBoletaArmadoDto run(Integer ddjjId, String aporteCodigo, LocalDate intencionDePago);

}
