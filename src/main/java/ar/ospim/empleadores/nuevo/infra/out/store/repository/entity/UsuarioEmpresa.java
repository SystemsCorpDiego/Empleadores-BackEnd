package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_empresa")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioEmpresa {
	
	@EmbeddedId
	private UsuarioEmpresaPK pk;

	public UsuarioEmpresa(Integer userId, Integer empresaId){
		pk = new UsuarioEmpresaPK(userId, empresaId);
	}

	public Integer getUsuarioId(){
		return pk.getUsuarioId();
	}

	public Integer getEmpresaId() {
		return pk.getEmpresaId();
	}

}
