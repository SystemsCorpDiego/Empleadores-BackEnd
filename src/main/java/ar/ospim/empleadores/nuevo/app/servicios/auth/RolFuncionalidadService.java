package ar.ospim.empleadores.nuevo.app.servicios.auth;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.RolFuncionalidadAltaDto;

public interface RolFuncionalidadService {

	public void  actualizar(Short rolId, RolFuncionalidadAltaDto dato);
	public List<RolFuncionalidadAltaDto> consultarTipoUsuario();
	public List<RolFuncionalidadAltaDto> consultarTodosRol();
	public RolFuncionalidadAltaDto consultarRol(String rolDescrip);
	
}
