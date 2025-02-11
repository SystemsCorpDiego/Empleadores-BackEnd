package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

import ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo.UsuarioEnumException;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.UsuarioClaveBo;
import ar.ospim.empleadores.comun.exception.BusinessException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsuarioBO {
 
	private Integer id;

    private String descripcion;

    private boolean habilitado; 

	private boolean dfaHabilitado;

    private LocalDateTime ultimoLogin;

	private LocalDateTime previoLogin;

    private UsuarioClaveBo usuarioClaveBo;
    
	private RolBO rol;
 
	
	public UsuarioBO(String username){
        this.descripcion = username;
    }
	
    public UsuarioBO(Integer id, String username){
    	this.id = id;
        this.descripcion = username;
    }
    
    public UsuarioBO(Integer id, String nombre, Boolean habilitado, RolBO rol){
        this.id = id;
        this.descripcion = nombre;
        this.habilitado = habilitado;
        this.rol = rol;
    }

    public UsuarioBO(Integer id, String usuario, String clave, String salt, String hashAlgorithm) {
        this(id, usuario, false, false, clave, salt, hashAlgorithm);
    }
    
    public UsuarioBO(String usuario, String clave, String salt, String hashAlgorithm) {
        this(null, usuario, false, false, clave, salt, hashAlgorithm);
    }
    
    public UsuarioBO(Integer id, String usuario, boolean habilitado, boolean dfaHabilitado, String clave, String salt, String hashAlgorithm) {
        this(id, usuario, habilitado, false, clave, salt, hashAlgorithm, null, null, null);
    }
    
    public UsuarioBO(Integer id, String nombre, boolean habilitado, boolean dfaHabilitado, String clave, String salt, String hashAlgorithm, 
    		LocalDateTime ultimoLogin, LocalDateTime previoLogin, RolBO rol) {
    	validaciones(nombre);
        this.id = id;
        this.descripcion = nombre;
        this.habilitado = habilitado;
        this.dfaHabilitado = dfaHabilitado;
        this.ultimoLogin = ultimoLogin;
		this.previoLogin = previoLogin;
        this.usuarioClaveBo = new UsuarioClaveBo(clave, salt, hashAlgorithm);
        this.rol = rol;
    }
    

    public void setUsuarioClaveBo(String clave, String salt, String hashAlgorithm) {
        this.usuarioClaveBo = new UsuarioClaveBo(clave, salt, hashAlgorithm);
    }

    public String getClave(){
        return usuarioClaveBo != null ? usuarioClaveBo.getClave() : null;
    }

    public String getSalt(){
        return usuarioClaveBo != null ? usuarioClaveBo.getSalt() : null;
    }

    public String getHashAlgorithm(){
        return usuarioClaveBo != null ? usuarioClaveBo.getHashAlgorithm() : null;
    }
   
    private void validaciones(String username) {
        Objects.requireNonNull(username, () -> {
            throw new BusinessException(UsuarioEnumException.NULL_USUARIO.name(), "El usuario es obligatorio");
        });
    }
}
