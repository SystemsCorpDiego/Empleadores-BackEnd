package ar.ospim.empleadores.nuevo.app.servicios.usuario.interno;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;

public interface ConsultarUsuarioInterno {
	List<UsuarioInternoBO> run();
	UsuarioInternoBO run(Integer usuarioId);	
	UsuarioInternoBO getFromSession();
}
