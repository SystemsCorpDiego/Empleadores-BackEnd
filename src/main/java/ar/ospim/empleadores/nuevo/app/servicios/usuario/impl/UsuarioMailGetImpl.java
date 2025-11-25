package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioMailGet;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.interno.ConsultarUsuarioInterno;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioMailGetImpl implements UsuarioMailGet {

	private final EmpresaContactoService empresaContactoService;
	private final EmpresaService empresaService;
	private final ConsultarUsuarioInterno consultarUsuarioInterno; 
	private final UsuarioStorage usuarioStorage;
	
	@Override
	public String run(Integer usuarioId) {
		UsuarioBO usuario = usuarioStorage.getUsuario(usuarioId);
		return run( usuario);
	}
	
	@Override
	public String run(UsuarioBO usuario) {		
		
		EmpresaBO empresa = findEmpresa(usuario);
		if (  empresa != null ) {
			return empresaContactoService.consultarMailPpal(empresa.getId());			
		}
		
		UsuarioInternoBO usuarioInterno = findUsuarioInterno(usuario);
		if (usuarioInterno != null && usuarioInterno.getPersona() != null)
			return usuarioInterno.getPersona().getEmail();
		
		return null;
	}

	private UsuarioInternoBO findUsuarioInterno(UsuarioBO usuario)  {
		UsuarioInternoBO usuarioInterno = null;
		try {
			usuarioInterno = consultarUsuarioInterno.run(usuario.getId());			
		} catch(BusinessException be) {
			if (!be.getCodigo().equals("REGISTRO_INEXISTENTE_ID") ) {
				throw be;
			}			
		}
		return usuarioInterno;
	}
	
	private EmpresaBO findEmpresa(UsuarioBO usuario)  {
		EmpresaBO empresa = null;
		try {
			empresa = empresaService.getEmpresa(usuario.getDescripcion());			
		} catch ( BusinessException be) {
			if ( !be.getCodigo().equals("CUIT_INEXISTENTE")) {
				throw be;
			}
		} 
		return empresa;
	}
}
