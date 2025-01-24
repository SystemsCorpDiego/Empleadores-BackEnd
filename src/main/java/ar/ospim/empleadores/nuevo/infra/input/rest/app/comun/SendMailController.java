package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.MailBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("comun")
@RequiredArgsConstructor
public class SendMailController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.mail.prueba.empresaId}")
	private Integer empresaIdPrueba;
	
	 @Autowired
	 private MailService mailService;
	 
	 @Autowired
	 private EmpresaService empresaService;
	 @Autowired
	 private EmpresaContactoService empresaContactoService;
	 
	@GetMapping( value={"/sendMail/prueba"} )
	public ResponseEntity<Void>  send( HttpServletRequest request ) {
		
		MailBO email = new MailBO();
		email.setMsg("Prueba Mail");
		email.setTo("buenodiegomartin@gmail.com");
		email.setTitulo("SIGECO - Prueba Mail");
		email.setMsg("SIGECO - prueba cuerpo mail");
		
		logger.debug("request.getRequestURI(): %s", request.getRequestURI() );
		logger.debug("request.getRequestURL(): %s", request.getRequestURL() );
		 
		
		//mailService.run(email);
        
		//mailService.runMailActivacionCuenta("11111111111", "buenodiegomartin@yahoo.com.ar");
		
		EmpresaBO empresa = empresaService.getEmpresa(empresaIdPrueba);
		empresa.setContactos( empresaContactoService.consultar(empresaIdPrueba) );
		
		logger.debug(  "debug: " +  System.getProperty("debug")  );
		logger.debug(  "spring.profiles.active: " +  System.getProperty("spring.profiles.active")  );
		
		mailService.runMailCuentaEmpresaNuevaInfo(empresa);
		
		
		return ResponseEntity.ok( null );
	}

}
