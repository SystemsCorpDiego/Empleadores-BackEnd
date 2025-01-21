package ar.ospim.empleadores.nuevo.infra.input.rest.app.afiliado;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.AfiliadoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.afiliado.dto.AfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfiliadoActu;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("afiliados")
@RequiredArgsConstructor 
public class AfiliadoController {

	private final AfiliadoService service; 
	private final AfiliadoDtoMapper mapper;
	private final UsuarioInfo usuarioInfo;
	
	@GetMapping(value = "/cuil/{cuil}")
	public ResponseEntity<List<AfiliadoDto>> consultar(@PathVariable String cuil) {
		
		List<AfiliadoBO> reg = service.get(cuil);
		List<AfiliadoDto> rta =  mapper.map(reg) ;

		return ResponseEntity.ok( rta );		
	}
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto> registrarModi(@RequestBody AfiliadoDto dto) {		
		AfiliadoActu reg = new AfiliadoActu();
		reg.setCuil_titular( dto.getCuil() );
		reg.setApellido( dto.getApellido() );
		reg.setNombre( dto.getNombre() );
		reg.setEmpresa_id( usuarioInfo.getUsuarioLogeadoEmpresaId() );
		reg.setCreado_por( UsuarioInfo.getCurrentAuditor() );
		AfiliadoActu regNew = service.saveActu(reg);
		IdGeneradoDto rta = new IdGeneradoDto(regNew.getId());
		
		return ResponseEntity.created(null).body(rta); 
	}
	
}
