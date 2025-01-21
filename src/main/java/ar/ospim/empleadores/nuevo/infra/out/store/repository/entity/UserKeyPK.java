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
public class UserKeyPK implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3053291021636483828L;

	@Column(name = "usuario_id", nullable = false)
	private Integer userId;

	@Column(name = "key", nullable = false)
	private String key;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserKeyPK userKeyPK = (UserKeyPK) o;
		return Objects.equals(userId, userKeyPK.userId) && Objects.equals(key, userKeyPK.key);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, key);
	}
}
