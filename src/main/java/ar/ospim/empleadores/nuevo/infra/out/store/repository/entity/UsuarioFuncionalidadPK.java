package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UsuarioFuncionalidadPK implements Serializable {

	private static final long serialVersionUID = -5482098137325590681L;

	public static final Number UNDEFINED_ID = -1;

	@Column(name = "usuario_id", nullable = false)
	private Integer usuarioId;

	@Column(name = "funcionalidad_codigo", nullable = false)
	private String funcionalidadCodigo;
 
	
	public UsuarioFuncionalidadPK(Integer usuarioId, String funcionalidadCodigo) {
		this.usuarioId = usuarioId;
		this.funcionalidadCodigo = funcionalidadCodigo;		
	}
}
