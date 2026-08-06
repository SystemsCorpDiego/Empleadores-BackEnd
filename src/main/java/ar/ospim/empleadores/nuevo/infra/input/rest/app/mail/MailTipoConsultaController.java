package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.mail.MailTipoConsultaService;
import ar.ospim.empleadores.nuevo.dominio.MailTipoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto.MailTipoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto.MailTipoDtoMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("mail-tipos")
@RequiredArgsConstructor
public class MailTipoConsultaController {

	private final MailTipoConsultaService service;
	private final MailTipoDtoMapper mapper;

	@GetMapping
	public ResponseEntity<List<MailTipoDto>> consultar() {
		List<MailTipoBO> consulta = service.consultar();
		List<MailTipoDto> consultaDto = mapper.map(consulta);
		return ResponseEntity.ok(consultaDto);
	}

}
