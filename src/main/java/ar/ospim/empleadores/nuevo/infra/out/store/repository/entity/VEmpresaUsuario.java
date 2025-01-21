package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "v_empresa_usuario")
@Immutable
@Setter
@Getter
@ToString
public class VEmpresaUsuario {

	@Id
	@Column(name = "usuario_id")
	private Integer usuarioId;

	@Column(name = "nombre", nullable = false, unique = true, length = 100)
	private String nombre;

	@Column(name = "habilitado")
	private Boolean habilitado = false;

	@Column(name = "ultimo_login")
	private LocalDateTime ultimoLogin;

	@Column(name = "empresa_id")
	private Integer empresaId;

	@Column(name = "dfa_habilitado")
	private Boolean dfaHabilitado = false;
}
