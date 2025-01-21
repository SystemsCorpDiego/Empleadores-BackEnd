package ar.ospim.empleadores.comun.seguridad;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.FuncionalidadRepository;
import lombok.RequiredArgsConstructor;

 @Service
@RequiredArgsConstructor
public  class UsuarioInfo {
	
	@Autowired
	private final UsuarioStorage storage;
	@Autowired
	private final FuncionalidadRepository repo;
	@Autowired
	private final EmpresaService empresaService;
	
	public static Integer getCurrentAuditor() {
		return SecurityContextUtils.getUserDetails().userId;
	}
	
	public Boolean validarEmpresa(Integer empresaId) {
		if ( validarEmpresaDelUsuarioLogueado(empresaId)  )
			return true;
		if ( validarCuitTest(empresaId)  )
			return true;
		
		return false;		
	}
	
	private Boolean validarEmpresaDelUsuarioLogueado(Integer empresaId) {
		Optional<Integer> cons = storage.getUsuarioIdByEmpresaId(empresaId);
		if ( cons.isEmpty() ) {
			return false;
		}
		if ( !cons.get().equals( getCurrentAuditor() ) ) {
			return false;
		}
		return true;
	}
	
	private Boolean validarCuitTest(Integer empresaId) {
		Integer canti = repo.getFuncTestCuitHabi(getCurrentAuditor());
		if ( canti >0) {
			Optional<EmpresaBO> cons = empresaService.findEmpresa(empresaId);
			if ( cons.isPresent() && cons.get().getCuit().equals("11111111111")) {
				return true;
			}
		}
		return false;
	}

	public Integer getUsuarioLogeadoEmpresaId() {
		Optional<Integer> cons = storage.getEmpresaIdByUsuarioId( UsuarioInfo.getCurrentAuditor() );
		if ( cons.isEmpty() ) {
			return null;
		}
	
		return cons.get();
	}

}
