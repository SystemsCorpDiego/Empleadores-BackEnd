package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "usuario_key")
@Getter
@Setter
@NoArgsConstructor
public class UserKey {

	private static final long serialVersionUID = -7104748562864239397L;

	@EmbeddedId
	private UserKeyPK pk;

	public UserKey(Integer userId, String key){
		pk = new UserKeyPK(userId, key);
	}

	public Integer getUserId(){
		return pk.getUserId();
	}
}
