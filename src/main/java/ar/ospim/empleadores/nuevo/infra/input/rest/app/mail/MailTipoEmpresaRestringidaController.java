package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailTipoEmpresaRestringidaService;
import ar.ospim.empleadores.nuevo.dominio.MailTipoEmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.EmpresaRestringidaMailDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto.MailTipoEmpresaRestringidaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("mail-tipos/deshabilitar-cuit")
@RequiredArgsConstructor
public class MailTipoEmpresaRestringidaController {

	private final MailTipoEmpresaRestringidaService service;
	private final EmpresaRestringidaMailDtoMapper mapper;

	@PostMapping
	public ResponseEntity<IdGeneradoDto> agregar(@RequestBody @Valid MailTipoEmpresaRestringidaDto dato, HttpServletRequest request) {
		MailTipoEmpresaRestringidaBO registro = mapper.map(dato);
		registro = service.registrar(registro);
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());

		URI location = URI.create(String.format(request.getRequestURI() + "%s", registro.getId()));

		return ResponseEntity.created(location).body(rta);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody @Valid MailTipoEmpresaRestringidaDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());

		MailTipoEmpresaRestringidaBO registro = mapper.map(id, dato);
		registro = service.registrar(registro);

		log.debug("FIN");
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public void borrar(@PathVariable Integer id) {
		service.borrar(id);
	}

	@GetMapping
	public ResponseEntity<List<MailTipoEmpresaRestringidaDto>> consultar(@RequestParam(required = false) Integer mailId) {
		List<MailTipoEmpresaRestringidaBO> consulta = service.consultar(mailId);
		List<MailTipoEmpresaRestringidaDto> consultaDto = mapper.map(consulta);
		return ResponseEntity.ok(consultaDto);
	}
}
