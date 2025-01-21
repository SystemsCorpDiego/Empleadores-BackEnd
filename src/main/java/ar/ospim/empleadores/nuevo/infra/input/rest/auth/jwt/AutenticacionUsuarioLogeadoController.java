package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInfoBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.ConsultarUsuarioLogueado;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.UsuarioInfoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.UsuarioInfoDtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth/login/usuario")
@AllArgsConstructor
public class AutenticacionUsuarioLogeadoController {

	private final ConsultarUsuarioLogueado consultarUsuarioLogueado;
	private final UsuarioInfoDtoMapper mapper;
	
	@GetMapping()
	public ResponseEntity<UsuarioInfoDto>  loginConsulta() {
		
		UsuarioInfoBO reg = consultarUsuarioLogueado.run();
		UsuarioInfoDto dto = mapper.map(reg);
		
		return ResponseEntity.ok( dto );
	}

}
