package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name="clave_reset_token")
@Getter
@Setter
@NoArgsConstructor
public class ClaveResetToken implements Serializable {

	private static final long serialVersionUID = -998394164286030241L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "token", nullable = false, length = 200)
	private String token;

	@Column(name = "usuario_id", nullable = false)
	private Integer usuarioId;

	@Column(name = "habilitado", nullable = false)
	private Boolean habilitado ;

	@Column(name = "baja_fecha", nullable = false)
	private LocalDateTime bajaFecha;


}
