package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.infra.input.servicio.UsuarioExternalService;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioEmpresaInfoBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.ConsultarEmpresa;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaUsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaUsuarioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioEmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioEmpresa;

@Service
public class EmpresaUsuarioStorageImpl implements EmpresaUsuarioStorage {
	
    private final Short perfilEmpleadorId; 
	private final UsuarioExternalService userExternalService;
	private final ConsultarEmpresa consultarEmpresa;
	private final EmpresaUsuarioRepository empresaUsuarioRepository; //Vista
	private final UsuarioEmpresaRepository usuarioEmpresaRepository;
	
    public EmpresaUsuarioStorageImpl(UsuarioExternalService userExternalService,
    		ConsultarEmpresa consultarEmpresa,
    		EmpresaUsuarioRepository empresaUsuarioRepository,
    		UsuarioEmpresaRepository usuarioEmpresaRepository,
    		@Value("${app.seguridad.perfilEmpleador:.+}") Short perfilEmpleadorId
    		) {		 
    	this.userExternalService = userExternalService;
    	this.consultarEmpresa = consultarEmpresa;
		this.empresaUsuarioRepository = empresaUsuarioRepository;
		this.usuarioEmpresaRepository = usuarioEmpresaRepository;
		this.perfilEmpleadorId = perfilEmpleadorId;
	}
	    
    @Override
    public String crearTokenClaveReset(Integer userId) {
        return userExternalService.crearTokenClaveReset(userId);
    }

    @Override
    public void habilitarUsuario(Integer userId) {
        empresaUsuarioRepository.findById(userId)
                .ifPresent(empresaUsuario ->
                        userExternalService.habilitarUsuario(empresaUsuario.getNombre()));
    }

    @Override
    public void deshabilitarUsuario(Integer userId) {
    	empresaUsuarioRepository.findById(userId)
                .ifPresent(empresaUsuario ->
                        userExternalService.deshabilitarUsuario(empresaUsuario.getNombre()));
    }

    @Override
    public void registrarUsuario(String nombre, String email, String clave) {
    	RolBO rol = new RolBO();
    	rol.setId(perfilEmpleadorId); 
        userExternalService.registrarUsuario(nombre, email, clave, rol);
    }

    @Override
    public void saveUsuarioEmpresa(Integer userId, Integer empresaId) {
    	usuarioEmpresaRepository.save(new UsuarioEmpresa(userId, empresaId));
    }
    
    @Override
	public void resetDFA(Integer userId) {
		userExternalService.resetDFA(userId);
	}

    @Override
    public Optional<UsuarioEmpresaInfoBO> getUsuarioEmpresaInfo(Integer userId) {
    	UsuarioEmpresaInfoBO usuarioEmpresaInfoBO = null;
    	Optional<Integer> consUE = usuarioEmpresaRepository.getEmpresaIdByUsuarioId(userId);
    	if ( consUE.isPresent()) {
    		Integer empresaId = consUE.get();
    		Optional<EmpresaBO> consE =consultarEmpresa.findEmpresa(empresaId);    		
    		if ( consE.isPresent()) {
    			EmpresaBO empre = consE.get();
    			usuarioEmpresaInfoBO = new UsuarioEmpresaInfoBO();
    			usuarioEmpresaInfoBO.setId(userId);
    			usuarioEmpresaInfoBO.setEmpresaId(empresaId);
    			usuarioEmpresaInfoBO.setCuit( empre.getCuit() );
    			//usuarioEmpresaInfoBO.setEmail(empre.get);
    			usuarioEmpresaInfoBO.setRazonSocial(empre.getRazonSocial());   		
    			usuarioEmpresaInfoBO.setActividadMolinera(empre.isActividadMolinera());
    			
    		}
    	}
    	return Optional.of(usuarioEmpresaInfoBO);
    	/*
        return userExternalService.getUsuario(userId)
                .map(userInfoDto -> {
                	UsuarioEmpresaInfoBO result = new UsuarioEmpresaInfoBO();
                    result.setId(userInfoDto.getId());
                    result.setEmail(userInfoDto.getUsuario());
					result.setPrevioLogin(userInfoDto.getUltimoLogin());
                    return result;
                })
                .map(this::loadEmpresaInfo);
                */
    }
    
    private UsuarioEmpresaInfoBO loadEmpresaInfo(UsuarioEmpresaInfoBO usuarioEmpresaInfoBo) {
    	usuarioEmpresaRepository.getByUsuarioId(usuarioEmpresaInfoBo.getId())
                .map(usuarioEmpresa -> consultarEmpresa.getBasicDataEmpresa(usuarioEmpresa.getEmpresaId()))
                .ifPresent(basicDataEmpresaDto -> {
                	usuarioEmpresaInfoBo.setEmpresaId(basicDataEmpresaDto.getId());
                	usuarioEmpresaInfoBo.setRazonSocial(basicDataEmpresaDto.getRazonSocial());
                	usuarioEmpresaInfoBo.setCuit(basicDataEmpresaDto.getCuit());
                });
        return usuarioEmpresaInfoBo;
    }
}
