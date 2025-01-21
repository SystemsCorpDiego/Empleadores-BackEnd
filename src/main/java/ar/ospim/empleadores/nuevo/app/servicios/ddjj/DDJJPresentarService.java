package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPresentarResponseDto;

public interface DDJJPresentarService {
	public DDJJPresentarResponseDto run(Integer ddjjId);
}
