package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.DeudaNominaMailConfigService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.DeudaNominaMailConfigDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper.DeudaNominaMailConfigDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("gestionDeuda/mail/config")
@RequiredArgsConstructor
public class GestionDeudaMailConfigController {

	private final DeudaNominaMailConfigService service;
	private final DeudaNominaMailConfigDtoMapper mapper;

	@GetMapping
	public ResponseEntity<DeudaNominaMailConfigDto> consultar() {
		DeudaNominaMailConfigBO reg = service.consultar();
		if (reg == null)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(mapper.map(reg));
	}

	@PostMapping
	public ResponseEntity<DeudaNominaMailConfigDto> crear(@RequestBody @Valid DeudaNominaMailConfigDto dato, HttpServletRequest request) {
		DeudaNominaMailConfigBO reg = mapper.map(dato);
		reg = service.crear(reg);
		DeudaNominaMailConfigDto rta = mapper.map(reg);

		URI location = URI.create(String.format(request.getRequestURI() + "/%s", reg.getId()));
		return ResponseEntity.created(location).body(rta);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<DeudaNominaMailConfigDto> actualizar(@PathVariable Long id, @RequestBody @Valid DeudaNominaMailConfigDto dato) {
		log.debug("id: {} - dato: {}", id, dato);
		DeudaNominaMailConfigBO reg = mapper.map(id, dato);
		reg = service.actualizar(id, reg);
		return ResponseEntity.ok(mapper.map(reg));
	}

}
