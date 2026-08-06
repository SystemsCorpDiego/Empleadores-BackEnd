package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.mail.MailTipoConfiguracionService;
import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.MailTipoConfiguracionDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto.MailTipoConfiguracionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("mail-tipos/config")
@RequiredArgsConstructor
public class MailTipoConfiguracionController {

	private final MailTipoConfiguracionService service;
	private final MailTipoConfiguracionDtoMapper mapper;

	@GetMapping
	public ResponseEntity<List<MailTipoConfiguracionDto>> consultar(@RequestParam(required = false) Integer mailId) {
		List<MailTipoConfiguracionBO> lst = service.consultar(mailId);
		return ResponseEntity.ok(mapper.map(lst));
	}

	@PostMapping
	public ResponseEntity<MailTipoConfiguracionDto> crear(@RequestBody @Valid MailTipoConfiguracionDto dato, HttpServletRequest request) {
		MailTipoConfiguracionBO reg = mapper.map(dato);
		reg = service.crear(reg);
		MailTipoConfiguracionDto rta = mapper.map(reg);

		URI location = URI.create(String.format(request.getRequestURI() + "/%s", reg.getId()));
		return ResponseEntity.created(location).body(rta);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<MailTipoConfiguracionDto> actualizar(@PathVariable Integer id, @RequestBody @Valid MailTipoConfiguracionDto dato) {
		log.debug("id: {} - dato: {}", id, dato);
		MailTipoConfiguracionBO reg = mapper.map(id, dato);
		reg = service.actualizar(id, reg);
		return ResponseEntity.ok(mapper.map(reg));
	}

}
