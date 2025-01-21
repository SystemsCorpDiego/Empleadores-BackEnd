package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ar.ospim.empleadores.comun.cui.CuilUtils;
import lombok.ToString;

@Entity
@Table(name = "afiliado")
@ToString
public class Afiliado extends AfiliadoBase {

	private static final long serialVersionUID = 4000407729869754072L;

	@EmbeddedId
	private AfiliadoId id;

	@JsonBackReference
	@Transient
	private DDJJEmpleado ddjjAfiliado;

	@Transient
	private boolean isNuevo;

	public Afiliado() {

	}

	public Afiliado(Afiliado afiliado) {
		super(afiliado);
		this.id = new AfiliadoId();
		id.cuil_titular = afiliado.id.cuil_titular;
		id.inte = afiliado.id.inte;
	}

	public Afiliado(String cuil, int inte, String apellido, String nombre) {
		super(cuil, inte, apellido, nombre);
		id = new AfiliadoId();
		this.id.cuil_titular = cuil;
		this.id.inte = inte;
	}

	public Afiliado(String cuilOriginal, int inteOriginal) {
		id = new AfiliadoId();
		this.id.cuil_titular = cuilOriginal;
		this.id.inte = inteOriginal;
	}

	public String getCuil_titular() {
		if (id == null) {
			id = new AfiliadoId();
		}
		return id.cuil_titular;
	}

	public void setCuil_titular(String cuil_titular) {
		if (id == null) {
			id = new AfiliadoId();
		}
		this.id.cuil_titular = cuil_titular;
	}

	public int getInte() {
		if (id == null) {
			id = new AfiliadoId();
		}
		return id.inte;
	}

	public void setInte(int inte) {
		if (id == null) {
			id = new AfiliadoId();
		}
		this.id.inte = inte;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(AfiliadoId id) {
		this.id = id;
	}

	public AfiliadoId getId() {
		return id;
	}

	public DDJJEmpleado getDdjjAfiliado() {
		return ddjjAfiliado;
	}
	

	public void setDdjjAfiliado(DDJJEmpleado ddjjAfiliado) {
		this.ddjjAfiliado = ddjjAfiliado;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Afiliado other = (Afiliado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Embeddable
	@ToString
	public static class AfiliadoId implements Serializable {
		private static final long serialVersionUID = -1707531170121244007L;
		private String cuil_titular;
		private int inte;

		public AfiliadoId() {

		}

		public String getCuil_titular() {
			return cuil_titular;
		}

		public void setCuil_titular(String cuil_titular) {
			this.cuil_titular = cuil_titular;
		}

		public int getInte() {
			return inte;
		}

		public void setInte(int inte) {
			this.inte = inte;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((cuil_titular == null) ? 0 : cuil_titular.hashCode());
			result = prime * result + inte;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AfiliadoId other = (AfiliadoId) obj;
			if (cuil_titular == null) {
				if (other.cuil_titular != null)
					return false;
			} else if (!cuil_titular.equals(other.cuil_titular))
				return false;
			if (inte != other.inte)
				return false;
			return true;
		}

	}

	public void setIsNuevo(boolean isNuevo) {
		this.isNuevo = isNuevo;
	}

	public boolean isNuevo() {
		return this.isNuevo;
	}

	public boolean isCuilValido() {
		if (id.cuil_titular != null && !CuilUtils.validar(id.cuil_titular)) {
			return false;
		}
		return true;
	}

}

