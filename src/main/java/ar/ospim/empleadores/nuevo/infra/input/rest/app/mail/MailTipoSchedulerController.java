package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.mail.MailTipoNotifScheduledService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailTipoSchedulerController {

	private final MailTipoNotifScheduledService service;
	
	
	@GetMapping(value = "/mail-tipos/enviar")
	public ResponseEntity<?> get() {		
		service.run();		
		return ResponseEntity.ok( null );
	}
			

}
