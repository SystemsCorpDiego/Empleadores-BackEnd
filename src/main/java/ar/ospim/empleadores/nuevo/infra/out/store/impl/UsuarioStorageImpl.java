package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.UsuarioStorageEnumException;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.RolStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioClaveRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioEmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRolRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Usuario;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioClave;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioRol;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.UsuarioPorMailI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioStorageImpl  implements UsuarioStorage  {
    public static final String PASSWORD_CON_ID_INEXISTENTE = "Clave con id (%s) inexistente";
   
    private final MessageSource messageSource;
    
    private final UsuarioRepository usuarioRepository;    
    private final UsuarioClaveRepository usuarioClaveRepository;
    private final UsuarioEmpresaRepository usuarioEmpresaRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolStorage rolStorage;
    private final UsuarioMapper mapper; 
    
    @Override
    public UsuarioBO guardar(UsuarioBO user) {
        log.debug("Save user in repository -> {}", user);
        Usuario userEntity = mapUsuario(user);
        userEntity = usuarioRepository.save(userEntity);
        user.setId(userEntity.getId());
        
        //leer usuario_clave
        Optional<UsuarioClave> consUsuarioClave = usuarioClaveRepository.findById(user.getId());
        UsuarioClave usuarioClave;
        if ( consUsuarioClave.isPresent() ) {
        	usuarioClave = consUsuarioClave.get();         	
        	usuarioClave.setClave(user.getClave());
        	usuarioClave.setSalt(user.getSalt());
        	usuarioClave.setHashAlgorithm(user.getHashAlgorithm());        	
        } else {
        	usuarioClave = mapUsuarioClave(user, userEntity);
        	usuarioClave.setDeleted(false);
        }
        
        if(user.getClave()!=null) 
        	usuarioClaveRepository.save(usuarioClave);
        
        usuarioRolRepository.deleteByUserId(user.getId());
        
        if ( user.getRol() !=null ) {
        	UsuarioRol usuarioRol = new UsuarioRol();
        	usuarioRol.setUserId( user.getId() );
        	usuarioRol.setRoleId( user.getRol().getId() );
        	usuarioRolRepository.save(usuarioRol);
        } 
        return user;
    }

    @Override
    public UsuarioBO actualizar(UsuarioBO userBo) {
        log.debug("Save user in repository -> {}", userBo);
        
        Usuario user = usuarioRepository.findById(userBo.getId())
                .orElseThrow(() -> {
                	String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.USUARIO_ID_NOT_FOUND.getMsgKey(), null, new Locale("es"));
                	return new BusinessException(UsuarioStorageEnumException.USUARIO_ID_NOT_FOUND.name(),
                  String.format(errorMsg, userBo));
                });
        UsuarioClave usuarioClave = usuarioClaveRepository.findById(userBo.getId())
                .orElse(null);

        actualizarUsuario( user, userBo);
        try {
            usuarioRepository.save(user);
        }catch (Exception e){
        	String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.USUARIO_DUPLICADO.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(UsuarioStorageEnumException.USUARIO_DUPLICADO.name(),
            		errorMsg);
        }
        
        if(userBo.getClave()!=null) {
            usuarioClave = (usuarioClave != null)? updateUserPassword(userBo, usuarioClave)
                    : mapUsuarioClave(userBo, user);
            usuarioClaveRepository.save(usuarioClave);
        }

        
        
        if ( userBo.getRol() ==null ) {
        	usuarioRolRepository.deleteByUserId(user.getId());
        } else {
            UsuarioRol usuarioRol = null;
            List<UsuarioRol> lstRol =usuarioRolRepository.findByUserId(user.getId());
            if (lstRol.size() > 0 ) {
            	usuarioRol = lstRol.get(0);
            }
            if ( usuarioRol ==  null || !usuarioRol.getRoleId().equals( userBo.getRol().getId() ) ) {
            	if ( usuarioRol != null ) {
            		usuarioRolRepository.deleteByUserId(user.getId());
            	}
            	usuarioRol = new UsuarioRol();
            	usuarioRol.setUserId( userBo.getId() );
            	usuarioRol.setRoleId( userBo.getRol().getId() );
            	usuarioRolRepository.save(usuarioRol);
            }
        }
         
        
        return actualizarUsuario( userBo, user);
    }

    private UsuarioClave updateUserPassword(UsuarioBO userBo, UsuarioClave usuarioClave) {
    	usuarioClave.setClave(userBo.getClave());
    	usuarioClave.setSalt(userBo.getSalt());
    	usuarioClave.setHashAlgorithm(userBo.getHashAlgorithm());
        return usuarioClave;
    }

    private Usuario actualizarUsuario(Usuario user, UsuarioBO userBo) {
        user.setDescripcion(userBo.getDescripcion());
        user.setHabilitado(userBo.isHabilitado());
        user.setUltimoLogin(userBo.getUltimoLogin());
		user.setPrevioLogin(userBo.getPrevioLogin());
        return user;
    }
    private UsuarioBO actualizarUsuario(UsuarioBO userBo, Usuario user) {
    	userBo.setDescripcion(user.getDescripcion());
    	userBo.setHabilitado(user.getHabilitado());
    	userBo.setUltimoLogin(user.getUltimoLogin());
    	userBo.setPrevioLogin(user.getPrevioLogin());
        return userBo;
    }
    
    @Override
    public UsuarioBO getUsuario(Integer userId) {
        log.debug("Get user in repository -> {}", userId);
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> {
                	String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.USUARIO_ID_NOT_FOUND.getMsgKey(), null, new Locale("es"));
                		return new BusinessException(UsuarioStorageEnumException.USUARIO_ID_NOT_FOUND.name(),
                        String.format(errorMsg, userId));
                });
        
        return mapUsuarioBo(user);
    }

    @Override
    public UsuarioBO getUsuario(String usuarioNombre) {
        log.debug("Get user in repository -> {}", usuarioNombre);
        Usuario usuario = usuarioRepository.findByUsuario(usuarioNombre)
                .orElseThrow(() -> {
                	String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.USUARIO_NOT_FOUND.getMsgKey(), null, new Locale("es"));	
                	return new BusinessException(
                			UsuarioStorageEnumException.USUARIO_NOT_FOUND.name(),
                			String.format(errorMsg, usuarioNombre)
                	);
                });
        
        return mapUsuarioBo(usuario);
    }
    
    public UsuarioBO getUsuarioPorMail(String mail) {
    	UsuarioBO usuario = null;
    	
    	//Busco Usuario x mail de la persona
    	List<UsuarioPorMailI> cons = usuarioRepository.findByMail(mail);
    	if ( cons != null && cons.size() == 1) {
    		usuario = new UsuarioBO();
    		usuario.setId( cons.get(0).getId() );
    		usuario.setDescripcion( cons.get(0).getDescripcion() );
    		usuario.setDfaHabilitado( cons.get(0).getDfaHabilitado() );
    		return usuario;
    	}
    	
    	//Busco usuario x Mail de la Empresa.
    	List<Integer> cons2 = usuarioEmpresaRepository.findByMail(mail);
    	if ( cons2 != null && cons2.size() == 1) {
    		Optional<Usuario> reg = usuarioRepository.findById(cons2.get(0));
    		if ( reg.isEmpty()  ) 
    			return null;
    		
    		usuario = mapper.map( reg.get() );
    		return usuario; 
    	}
    	
    	return null;
    }
    
    @Override
    public boolean existe(String usuarioNombre) {
        log.debug("existe user in repository -> {}", usuarioNombre);
        Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioNombre);
        if ( usuario.isPresent() ) 
        	return true;
        
        return false;
    }
    
    @Override
    public Optional<Integer> getUsuarioIdByEmpresaId(Integer empresaId) {
    	return usuarioEmpresaRepository.getUsuarioIdByEmpresaId(empresaId);
    }

    @Override
    public Optional<Integer> getEmpresaIdByUsuarioId(Integer usuarioId) {
    	return usuarioEmpresaRepository.getEmpresaIdByUsuarioId(usuarioId);
    }

    
    private UsuarioBO mapUsuarioBo( Usuario usuario ) {
    	RolBO rolBo = null;
        List<UsuarioRol> lst = usuarioRolRepository.findByUserId(usuario.getId());
        if ( lst.size() > 0) {
        	Optional<RolBO> rolFind = rolStorage.findById( lst.get(0).getRoleId() );
        	if ( rolFind.isPresent()) {
        		rolBo = rolFind.get();
        	}
        } 
        
        UsuarioClave usuarioClave = usuarioClaveRepository.findById(usuario.getId()).orElse(null);
        
        return mapUsuarioBo(usuario, usuarioClave, rolBo);
    }
    
	@Override
	public void resetDFA(Integer userId) {
		log.debug("Reset two factor authentication -> {}", userId);
		usuarioRepository.resetDFA(userId);
	}

	public void setDFAToken(Integer userId, String tokenDFA) {
		log.debug("DFA - token: Actualizar -> {}", userId);
		usuarioRepository.setDFASecreto(userId, tokenDFA);
	}
	
    @Override
    public Boolean usuarioConDFAHabilitado(Integer userId) {
        log.debug("Fetch user has two factor authentication enabled -> {}", userId);
        return usuarioRepository.usuarioConDFAHabilitado(userId);
    }


    private UsuarioBO mapUsuarioBo(Usuario user, UsuarioClave userPassword, RolBO rol) {
        return (userPassword!=null) ?
                new UsuarioBO( 
                user.getId(),
                user.getDescripcion(),
                user.getHabilitado(),
                userPassword.getClave(),
                userPassword.getSalt(),
                userPassword.getHashAlgorithm(),
                user.getUltimoLogin(),
				user.getPrevioLogin(), rol):
                new UsuarioBO(user.getId(),
                        user.getDescripcion(),
                        user.getHabilitado(),
                        rol
                        );
    }

    private UsuarioClave mapUsuarioClave(UsuarioBO user, Usuario userEntity) {
    	UsuarioClave result = new UsuarioClave();
        result.setId(userEntity.getId());
        result.setClave(user.getClave());
        result.setSalt(user.getSalt());
        result.setHashAlgorithm(user.getHashAlgorithm());

        return result;
    }

    private Usuario mapUsuario(UsuarioBO user) {
        Usuario result = new Usuario();
        result.setId(user.getId());
        result.setDescripcion(user.getDescripcion());
        result.setHabilitado(user.isHabilitado());
        result.setUltimoLogin(user.getUltimoLogin());
        return result;
    }

	@Override
	public void habilitarCuenta(Integer usuarioId) {
		// TODO Auto-generated method stub
		Optional<Usuario> consUsuario = usuarioRepository.findById(usuarioId);
		Usuario usuario = null;
        if ( consUsuario.isPresent() ) {
        	usuario = consUsuario.get();         	
        	usuario.setHabilitado(true);
        	usuarioRepository.save(usuario);
        }
	}
	
	@Override
	public void desHabilitarCuenta(Integer usuarioId) {
		usuarioRepository.deshabilitar(usuarioId);
	}
}
