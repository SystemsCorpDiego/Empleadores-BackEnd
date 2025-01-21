package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioRecupoClave;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioPersonaRepository;

public class UsuarioRecupoClaveImpl implements UsuarioRecupoClave {

	@Autowired
	private UsuarioPersonaRepository usuarioPersonaRepository; 
	
	
	public void run(String mail) {
		
	}

}
