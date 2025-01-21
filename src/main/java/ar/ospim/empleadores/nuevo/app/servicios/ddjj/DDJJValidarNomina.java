package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarCuilDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarErrorDto;

public interface DDJJValidarNomina {
	//valida PK: cuiles
	public Optional<List<DDJJValidarErrorDto>> run(DDJJBO ddjj) ;
	
	public List<DDJJValidarCuilDto> run(List<DDJJValidarCuilDto> lstCuiles);
	
	public boolean esValida(DDJJBO ddjj) ;
	
}
