package ar.ospim.empleadores.nuevo.app.servicios.usuario.empresa;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.GenerarDFA;
import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.auth.usuario.app.UsuarioRolAsignacionService;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.ValidatorClave;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.CrearEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.RegistrarUsuarioEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaUsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaContactoTipoEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrearUsuarioEmpresaImpl implements CrearUsuarioEmpresa {

    @Value("${app.seguridad.dfa.empleador:false}")
    private boolean dfaEmpleadorEnabled;

	private final CrearEmpresa crearEmpresa;
	private final EmpresaUsuarioStorage empresaUsuarioStorage;
	private final UsuarioRolAsignacionService usuarioRolAsignacionService;
	private final UsuarioStorage usuarioStorage;
	private final ValidatorClave validatorClave;
	private final MessageSource messageSource;
	private final GenerarDFA generateTwoFactorAuthentication;
	private final MailService mailService;
	
	@Override
	public EmpresaBO run(EmpresaBO empresa, String clave) {
		UsuarioBO usuarioBo = new UsuarioBO(empresa.getCuit());
		usuarioBo.setUsuarioClaveBo(clave, "", "");

		validacionesClave(clave);
		
		EmpresaBO empresaCreada = crearEmpresa.run(empresa);
		empresa.setId( empresaCreada.getId() );
		
		Optional<ContactoBO> mail = empresa.getContactos().stream()
		.filter(contacto -> EmpresaContactoTipoEnum.MAIL.equals( contacto.getTipo()))
		.findFirst();
		
		empresaUsuarioStorage.registrarUsuario(empresa.getCuit(), mail.get().getValor(), clave);
		
		UsuarioBO usuarioBO = usuarioStorage.getUsuario(empresa.getCuit());
		usuarioRolAsignacionService.saveUsuarioRol(usuarioBO.getId(), ERol.EMPLEADOR);
		
		//Registrar relacion Usuario-Empresa
		empresaUsuarioStorage.saveUsuarioEmpresa( usuarioBO.getId(), empresaCreada.getId() );
		
		//TODO:
		SetDFABo dfaDto = null;
		if ( dfaEmpleadorEnabled ) {        	
			dfaDto = generateTwoFactorAuthentication.run(usuarioBO.getId());
        }
		
		//TODO: envio mail de Activacion
		if ( dfaEmpleadorEnabled ) {        	
			mailService.runMailActivacionCuenta(usuarioBO, mail.get().getValor(), dfaDto);
        } else {
        	mailService.runMailActivacionCuenta(usuarioBO, mail.get().getValor());
        }
		mailService.runMailCuentaEmpresaNuevaInfo(empresaCreada);
		
		return empresa;
	}

	private void validacionesClave(String pwd) {
    	if ( !validatorClave.esValida(pwd) ) {
        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.PASSWORD_PATRON_INVALIDO.getMsgKey(), null, new Locale("es"));        	
        	throw new BusinessException(RegistrarUsuarioEnumException.PASSWORD_PATRON_INVALIDO.name(), errorMsg );
    	}
    }
}
