package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaNotifMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeudaNotifMailController {

	private final DeudaNotifMailService service;
	
	
	@GetMapping(value = "/deuda/mail-notif/enviar")
	public ResponseEntity<?> get() {
		
		service.run();
		
		return ResponseEntity.ok( null );
	}
			

}
