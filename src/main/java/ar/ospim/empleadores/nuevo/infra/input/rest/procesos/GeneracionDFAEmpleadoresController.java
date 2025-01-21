package ar.ospim.empleadores.nuevo.infra.input.rest.procesos;

import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.dfa.app.DeshabilitarDFA;
import ar.ospim.empleadores.auth.dfa.app.GenerarDFA;
import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.UsuarioExternalService;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.ConsultarEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoService;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/procesos/FDA/empleadores")
@RequiredArgsConstructor 
public class GeneracionDFAEmpleadoresController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ConsultarEmpresa empresaService;
	private final EmpresaContactoService empresaContactoService;
	private final UsuarioAuthenticationStorage usuarioAuthenticationStorage;
	private final UsuarioRepository usuarioRepository;
	private final GenerarDFA generateTwoFactorAuthentication;
	private final DeshabilitarDFA deshabilitarDFA;
	private final UsuarioExternalService usuarioService;
	private final MailService mailService;
	private final UsuarioStorage usuarioStorage;
	 
	@PostMapping("")
	public ResponseEntity<String> generarDFA() {		
		Integer maxRegProcesar = 50;
		Integer totalProcesados = 0;
		
		List<EmpresaBO> lstEmpresa = empresaService.findAll();
		
		for (EmpresaBO reg : lstEmpresa) {
			if ( totalProcesados <= maxRegProcesar) {
				if ( procesar(reg) ) {
					totalProcesados = totalProcesados +1;
				}
			}
		}
		return ResponseEntity.ok( "Total procesados: " + totalProcesados );
	}
	
	@PostMapping("/cuit/{sCUIT}")
	public ResponseEntity<String> generarDFACuit(@PathVariable("sCUIT") String cuit) {
		Boolean rta = false;
		EmpresaBO empresa = null;
		try {
			empresa = empresaService.getEmpresa(cuit);
		} catch (Exception e) {
			logger.error( "PROCESO-DFA-EMPLEADORES - ERROR - Exception: " + e.toString() );			
		}
		
		if ( empresa != null) { 
			Optional<Integer> usuario = usuarioRepository.getUsuarioId(cuit);
			if ( usuario.isPresent() ) {
				//Deshabilito usuario para q el proceso tome el dato
				usuarioService.deshabilitarUsuario(cuit);
				//Deshabilita la DFA que tenia.-
				deshabilitarDFA.run(usuario.get());
				
				rta = procesar(empresa);		
			} else {
				logger.error( "PROCESO-DFA-EMPLEADORES - ERROR - CUIT " + empresa.getCuit() + " - No se encontro el usuario para el CUIT.");
				return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body( "PROCESO-DFA-EMPLEADORES - ERROR - CUIT " + empresa.getCuit() + " - No se encontro el usuario para el CUIT." );
			}
		} else {
			//ResponseEntity.badRequest("");
			logger.error( "PROCESO-DFA-EMPLEADORES - ERROR - CUIT " + cuit + " - No se encontro empresa para el CUIT.");
			return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body( "PROCESO-DFA-EMPLEADORES - ERROR - CUIT " + cuit + " - No se encontro empresa para el CUIT.");
		}
		
		return ResponseEntity.ok( rta.toString()  );
	}
	
	private Boolean procesar(EmpresaBO empresa) {
		//Los Usuarios Empresa nacen DESHABILITADOS.
		//SOLO a los DESHABILITADOS, se les manda mail de activacion de cuenta y DFA.-
		//El mail manda link para que el usuario se habilite la cuenta y lleva el DFA en el texto.-
		
		ContactoBO mailPpal = getMail( empresa.getId() );
		if ( mailPpal != null ) {
			//Optional<Usuario> cons = usuarioRepository.findByUsuario(reg.getCuit()) ;
			UsuarioBO usuarioBO = null;
			try {
				usuarioBO = usuarioStorage.getUsuario(empresa.getCuit());
			} catch (Exception e) {
				logger.error( "PROCESO-DFA-EMPLEADORES - ERROR - Exception: " + e.toString() );
			}
			
			if ( usuarioBO != null ) {									 
				if ( !usuarioBO.isHabilitado()  && !usuarioBO.isDfaHabilitado() ) {
					Optional<String> dfaSecreto = usuarioAuthenticationStorage.getTwoFactorAuthenticationSecret(usuarioBO.getId());
					if ( !dfaSecreto.isPresent() ) {
						//Genero NUEVA DFA 					
						SetDFABo authenticationBo = generateTwoFactorAuthentication.run(usuarioBO.getId());
									
						//mando Mail con la clave
						 mailService.runMailActivacionCuenta(usuarioBO, mailPpal.getValor(), authenticationBo);
						 return true;
					} else {
						logger.error( "PROCESO-DFA-EMPLEADORES - VAL - CUIT: " + empresa.getCuit() + " - usuario con DFA secret registrado.");
					}					 
				} else {
					logger.error( "PROCESO-DFA-EMPLEADORES - VAL - CUIT " + empresa.getCuit() + " - usuarioBO.isHabilitado():"+usuarioBO.isHabilitado() + " - usuarioBO.isDfaHabilitado(): "+ usuarioBO.isDfaHabilitado() );
				}
			}  else {
				logger.error( "PROCESO-DFA-EMPLEADORES - ERROR - CUIT " + empresa.getCuit() + " - No se encontro el usuario para el CUIT.");
			}
		} else {
			logger.error( "PROCESO-DFA-EMPLEADORES - ERROR - CUIT " + empresa.getCuit() + " - Falta informar MAIL principal.");
		}
		return false;
	}
	
	private ContactoBO getMail(Integer id) {
		List<ContactoBO> contactos = empresaContactoService.consultar(id);
		for (ContactoBO reg : contactos) {
			if ( reg.esMailPpal() ) {
				return reg;
			}
		}
		return null;
	}
	
}
