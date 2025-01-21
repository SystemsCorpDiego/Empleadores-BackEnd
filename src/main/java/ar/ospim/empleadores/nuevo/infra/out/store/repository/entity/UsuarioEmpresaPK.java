package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UsuarioEmpresaPK implements Serializable {

	private static final long serialVersionUID = -5946738319917380726L;

	@Column(name = "usuario_id", nullable = false)
	private Integer usuarioId;

	@Column(name = "empresa_id", nullable = false)
	private Integer empresaId;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UsuarioEmpresaPK that = (UsuarioEmpresaPK) o;
		return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(empresaId, that.empresaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuarioId, empresaId);
	}

}
