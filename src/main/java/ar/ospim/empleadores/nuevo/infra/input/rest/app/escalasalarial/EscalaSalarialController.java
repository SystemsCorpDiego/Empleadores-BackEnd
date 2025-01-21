package ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.app.servicios.camara.CamaraService;
import ar.ospim.empleadores.nuevo.app.servicios.escalasalarial.EscalaSalarialService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.AntiguedadDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.CamaraDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.escalasalarial.dto.CategoriaDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EscalaSalarialController {
	private final EscalaSalarialService service;
	private final CamaraService camaraService;
	private final CamaraDtoMapper camaraMapper;	
	private final CamaraCategoriaDtoMapper categoriaMapper;
	
	@GetMapping("/camara")
	public List<CamaraDto> getCamara() {
		return camaraMapper.map(camaraService.getAllCamara());
	}

	@GetMapping("/camara/categoria")
	public List<CategoriaDto> getCategoria() { 
		return categoriaMapper.map(camaraService.getAllCategoria());
	}
	
	@GetMapping("/camara/antiguedad")
	public List<AntiguedadDto> getAntiguedades() { 
		List<AntiguedadDto> lst = service.getAntiguedades();
		return lst;		
	}	
	
	@GetMapping("/escala-salarial")
	public List<EscalaSalarialBO> get(@RequestParam String tipo, @RequestParam String camara, 
			@RequestParam String categoria, 
			@RequestParam Integer antiguedad, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE )  @Nullable @RequestParam LocalDate vigencia) {
		
		List<EscalaSalarialBO> cons = service.get(tipo, camara, categoria, antiguedad, vigencia);
		
		return cons;
	}
	
	
}
