package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.convenio.ConvenioService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ConvenioConsultaController {

	private final ConvenioMapper mapper;
	private final ConvenioService service;

	@GetMapping(value = "/convenios")
	public ResponseEntity<List<ConvenioConsultaDto>>  get(
			@Nullable @RequestParam Integer empresaId,
			@Nullable @RequestParam String estado, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate desde, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate hasta		
			) {
		
		List<ConvenioConsultaDto> lst = null; 
		
		ConvenioConsultaFiltroDto filtro = new ConvenioConsultaFiltroDto();
		filtro.setDesde(desde);
		filtro.setHasta(hasta);
		filtro.setEmpresaId(empresaId);
		filtro.setEstado(estado);
		
		List<Convenio> lstAux = service.get(filtro);
		lst = mapper.run4( lstAux );
		lst = service.addUsuarioDescrip(lst);
		
		return ResponseEntity.ok( lst );
	}
	
	
	@GetMapping(value = "/empresa/{empresaId}/convenios")
	public ResponseEntity<List<ConvenioConsultaDto>>  getByUsuarioInterno(@PathVariable("empresaId") Integer empresaId,
			@Nullable @RequestParam String estado, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate desde, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate hasta		
			) {
		
		List<ConvenioConsultaDto> lst = null; 
		
		ConvenioConsultaFiltroDto filtro = new ConvenioConsultaFiltroDto();
		filtro.setDesde(desde);
		filtro.setHasta(hasta);
		filtro.setEmpresaId(empresaId);
		filtro.setEstado(estado);
		
		List<Convenio> lstAux = service.get(filtro);
		lst = mapper.run4( lstAux );
		
		return ResponseEntity.ok( lst );
	}

}
