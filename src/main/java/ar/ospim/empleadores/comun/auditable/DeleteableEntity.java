package ar.ospim.empleadores.comun.auditable;

import java.time.LocalDateTime;

public interface DeleteableEntity<I> {

    I getDeletedBy();

    void setDeletedBy(I user);

    LocalDateTime getDeletedOn();

    void setDeletedOn(LocalDateTime dateTime);

    boolean isDeleted();
    
    void setDeleted(Boolean deleted);
}