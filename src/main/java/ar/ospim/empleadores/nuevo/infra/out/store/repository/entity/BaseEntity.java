package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 2160499439875191810L;
	
	@Override
	public abstract boolean equals(Object object);
	
	@Override
	public abstract int hashCode();
}
