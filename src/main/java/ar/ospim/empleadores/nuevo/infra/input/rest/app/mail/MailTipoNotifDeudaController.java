package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.mail.MailScheduledDeudaNotifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailTipoNotifDeudaController {

	private final MailScheduledDeudaNotifService service;
	
	
	@GetMapping(value = "/mail-tipos/notif-deuda/enviar")
	public ResponseEntity<?> get() {		
		service.run();		
		return ResponseEntity.ok( null );
	}
			

}
