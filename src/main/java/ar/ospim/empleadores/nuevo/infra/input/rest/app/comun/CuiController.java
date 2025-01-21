package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.afiliado.ValidarCUIL;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.ResultadoDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("comun")
@RequiredArgsConstructor
public class CuiController {
	
	private final ValidarCUIL validarCUIL;
	
	@GetMapping(value = "/cui/{cui}/validar")
	public ResponseEntity<ResultadoDto>  validar(@PathVariable("cui") String cui) {
		boolean resultado = validarCUIL.run(cui);
		return ResponseEntity.ok( new ResultadoDto(resultado)  );
	}	 
}
