package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "descripcion", nullable = false, unique = true, length = 100)
	private String descripcion;
	
	@Column(name = "habilitado", nullable = false)
	private Boolean habilitado = false;

	@Column(name = "ultimo_login")
	private LocalDateTime ultimoLogin;

	@Column(name = "previo_login")
	private LocalDateTime previoLogin;
	
	@Column(name = "dfa_secreto", length = 64)
	private String dfaSecreto;

	@Column(name = "dfa_habilitado", nullable = false)
	private Boolean dfaHabilitado = false;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", descripcion='" + descripcion + '\'' +
				", habilitado=" + habilitado +
				", ultimoLogin=" + ultimoLogin +
				", previoLogin=" + previoLogin +
				", dfaHabilitado=" + dfaHabilitado +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
}
