package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.MailBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;

@Service
public class MailServiceImpl implements MailService {
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired 
	 private HttpServletRequest request;
	 
	 @Value("${server.servlet.context-path}")
	 private String serverServletContextPath;
		    	  
	 @Autowired
	 private JavaMailSender emailSender;
	 
	 @Value("${spring.mail.username}")
	 private String fromMail;
	 
	 @Autowired
	private TokenGestionUsuario tokenActivacion;
	 
	 @Autowired
	 private UsuarioPersonaStorage storage;
	 
	 
	 @Value("${app.mail.cambio-clave.titulo}")
	 private String CC_titulo;
	 @Value("${app.mail.cambio-clave.cuerpo}")
	 private String CC_cuerpo;
	 
	 
	 @Value("${app.mail.activacion-cuenta.titulo}")
	 private String AC_titulo;
	 @Value("${app.mail.activacion-cuenta.cuerpo}")
	 private String AC_cuerpo;
	 @Value("${app.mail.activacion-cuenta.cuerpo-dfa}")
	 private String AC_cuerpo_dfa;
	 
	 @Value("${app.mail.cuenta-empresa-nueva.titulo}")
	 private String CEN_titulo;
	 @Value("${app.mail.cuenta-empresa-nueva.cuerpo}")
	 private String CEN_cuerpo;

	 @Value("${app.mail.recupero-clave.titulo}")
	 private String RC_titulo;
	 @Value("${app.mail.recupero-clave.cuerpo}")
	 private String RC_cuerpo;
	 @Value("${app.mail.recupero-clave.cuerpo-dfa}")
	 private String RC_cuerpo_dfa;

	 private String springProfile;
	 
	 public MailServiceImpl() {
		 springProfile = System.getProperty("spring.profiles.active") ; 
	 }
	 
	public void run(MailBO email) {

		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(fromMail);
        message.setTo(email.getTo()); 
        if ( springProfile.equals("dev") ) {
        	message.setSubject("(Desarrollo) - " + email.getTitulo());
        } else {
        	message.setSubject(email.getTitulo());
        }
        message.setText(email.getMsg());
        
        emailSender.send(message);	
	}

	@Override
	public void runCambioDeClave(String usuario, String claveNueva, String usuarioMail,  String usuarioModificaMail) {
		logger.error("MailService.runClaveNueva - INIT");
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			
			String cuerpo = String.format(CC_cuerpo, usuario, claveNueva);		
			mimeMessage.setContent(cuerpo, "text/html");
			
			//mimeMessage.setSubject(CC_titulo);
			if ( springProfile.equals("dev") ) {
				 mimeMessage.setSubject("(Desarrollo) - " + CC_titulo);
	        } else {
	        	mimeMessage.setSubject(CC_titulo);
		    }
			 
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(usuarioMail));
			if ( usuarioModificaMail != null)
				mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(usuarioModificaMail));
			
	        emailSender.send(mimeMessage);
	        logger.error("MailService.runClaveNueva - PASO emailSender.send(mimeMessage); !!!! ");
		} catch( Exception e) {
			logger.error("MailService.runClaveNueva - ERROR - usuario - -> {}", usuario);
			logger.error("MailService.runClaveNueva - ERROR - -> {}", e);
		}
	}

	@Override
	public void runMailActivacionCuenta(UsuarioBO usuarioBO, String usuarioMail) {
		try {
			String token = tokenActivacion.crearParaUsuario(usuarioBO);
			String sUrlLink = getUrlActivacionCuenta( token ); 
			String mailCuerpo = String.format(AC_cuerpo, sUrlLink);	
			
			runMailInt(usuarioMail, AC_titulo, mailCuerpo);
		} catch( Exception e) {
			logger.error("MailService.runMailActivacionCuenta - ERROR - -> {}", usuarioBO.toString());
			logger.error("MailService.runMailActivacionCuenta - ERROR - -> {}", e);
		}				
	}

	@Override
	public void runMailActivacionCuenta(UsuarioBO usuarioBO, String usuarioMail, SetDFABo  dfaDto) {
		try {
			String token = tokenActivacion.crearParaUsuario(usuarioBO, dfaDto.getSharedSecret());
			String sUrlLink = getUrlActivacionCuenta( token ); 
			String mailCuerpo = String.format(AC_cuerpo_dfa, usuarioBO.getDescripcion(), sUrlLink, dfaDto.getSharedSecret(), dfaDto.generateAuthenticatorBarCode());	
			
			runMailIntWithAttach(usuarioMail, AC_titulo, mailCuerpo);			 
		} catch( Exception e) {
			logger.error("MailService.runMailActivacionCuenta - DFA - ERROR - -> {}", usuarioBO.toString());
			logger.error("MailService.runMailActivacionCuenta - DFA - ERROR - -> {}", e);
		}				
	}

	
	public void runMailCuentaEmpresaNuevaInfo(EmpresaBO empresa) {
		try {
			List<String> lstMails = getMailsNotifAltaEmpre() ;
			if ( lstMails != null && lstMails.size() > 0 ) {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				
				//mimeMessage.setSubject(CEN_titulo);
				if ( springProfile.equals("dev") ) {
					 mimeMessage.setSubject("(Desarrollo) - " + CEN_titulo);
		        } else {
		        	mimeMessage.setSubject(CEN_titulo);
			    }
				
				String razonSocial = "(" + empresa.getCuit() + ") " + empresa.getRazonSocial();
				String cuerpo = String.format(CEN_cuerpo, razonSocial, getMailPpal(empresa), getTelPpal(empresa)  );	
				mimeMessage.setContent(cuerpo, "text/html");
				
				for ( String mail : lstMails) {
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
				}
		        emailSender.send(mimeMessage);
			}
		} catch( Exception e) {
			logger.error("MailService.runMailCuentaEmpresaNuevaInfo - ERROR - -> {}", e);			
		}	
	}

	@Override
	public void runMailRecuperoClave(String mail, String usuario, String token, SetDFABo dfaDto) {
		try {	
			String sUrlLink = getUrlRecuperoClave(token);
			String mailCuerpo = String.format(RC_cuerpo_dfa, usuario, sUrlLink, dfaDto.getSharedSecret(), dfaDto.generateAuthenticatorBarCode());
			runMailIntWithAttach(mail, RC_titulo, mailCuerpo);
		} catch( Exception e) {
			logger.error("MailService.runMailRecuperoClaveDFA - ERROR - -> {}", e);
		}
	}

	public void runMailRecuperoClave(String mail,  String usuario,String token) {
		try {			
			String sUrlLink = getUrlRecuperoClave(token);
			String mailCuerpo = String.format(RC_cuerpo, usuario, sUrlLink);	
			runMailInt(mail, RC_titulo, mailCuerpo);			 
		} catch( Exception e) {
			logger.error("MailService.runMailRecuperoClave - ERROR - -> {}", e);
		}
	}	
	
	private void runMailInt(String mailTo,  String mailAsunto,  String mailCuerpo) {
		try {			
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			
			//mimeMessage.setSubject(mailAsunto);
			if ( springProfile.equals("dev") ) {
				 mimeMessage.setSubject("(Desarrollo) - " + mailAsunto);
	        } else {
	        	mimeMessage.setSubject(mailAsunto);
		    }

			mimeMessage.setSubject(mailAsunto);
			mimeMessage.setContent(mailCuerpo, "text/html");
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
			
			emailSender.send(mimeMessage);
		} catch( Exception e) {
			logger.error("MailService.runMailRecuperoClave - ERROR - -> {}", e);
		}	
	}
	
	private void runMailIntWithAttach(String mailTo,  String mailAsunto,  String mailCuerpo) {
		try {			
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			
			
			//mimeMessage.setSubject(mailAsunto);
			if ( springProfile.equals("dev") ) {
				 mimeMessage.setSubject("(Desarrollo) - " + mailAsunto);
	        } else {
	        	mimeMessage.setSubject(mailAsunto);
		    }

			
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));

			Multipart emailContent=new MimeMultipart();
			MimeBodyPart textBodyPart=new MimeBodyPart();
			textBodyPart.setContent(mailCuerpo, "text/html; charset=utf-8");
			 
			
			//InputStream input = getClass().getResourceAsStream("resources/documentos/Ayuda-DFA-ProcedimientoActivacion.pdf");
			 File file2 = ResourceUtils.getFile("classpath:documentos/Ayuda-DFA-ProcedimientoActivacion.pdf");
			 
			MimeBodyPart jpgBodyPart=new MimeBodyPart();
			//getClass().getResourceAsStream("/resources/documentos/Ayuda-DFA-ProcedimientoActivacion.pdf");
			//URL url = getClass().getResource("Ayuda-DFA-ProcedimientoActivacion.pdf");
			//File file = new File(url.getPath());
			jpgBodyPart.attachFile(file2);
			
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(jpgBodyPart);
			
			mimeMessage.setContent(emailContent);
			emailSender.send(mimeMessage);
		} catch( Exception e) {
			logger.error("MailService.runMailRecuperoClave - ERROR - -> {}", e);
		}	
	}
	
	private String getUrlRecuperoClave(String token) {
		return  "http://" + getDomain() + serverServletContextPath + "/#/usuario/recuperar-clave/" + token;
	}
	
    private  String getUrlActivacionCuenta(String token) {
		return "http://" + getDomain() + serverServletContextPath + "/#/usuario/empresa/activar/" + token;
	}

	private List<String> getMailsNotifAltaEmpre() {
		List<String> rta = new ArrayList<String>();
		List<UsuarioInternoBO> lst = storage.findAllUsuarioInterno();
		for (UsuarioInternoBO reg : lst) {
			if ( reg.getPersona().getNotificaciones() ) {
				rta.add(reg.getPersona().getEmail());
			}
		}
		return rta;
	}
	
	private String getMailPpal(EmpresaBO empresa) {
		if ( empresa.getContactos() != null && empresa.getContactos().size()>0) {
			for( ContactoBO reg : empresa.getContactos() ) {
				if ( reg.esMailPpal() ) {
					return reg.getValor();
				}
			}
		}
		return "";
	}
	
	private String getTelPpal(EmpresaBO empresa) {
		if ( empresa.getContactos() != null && empresa.getContactos().size()>0) {
			for( ContactoBO reg : empresa.getContactos() ) {
				if ( reg.esTelefonoPpal() ) {
					return reg.getValor();
				}
			}
		}
		return "";
	}
	
	private String getDomain() {
		//request.getRequestURL();
		//request.getRequestURI();
		try {
			return request.getRequestURL().toString().split("/")[2];
		} catch ( Exception e ) {
			if ( request == null ) {
				logger.error("MailService.getDomain() - ERROR - request NULLL ");
			} else {
				logger.error("MailService.getDomain() - ERROR - request.getRequestURL():  ", request.getRequestURL());
			}
			return "";
		}
	}

}
