package ar.ospim.empleadores.comun.auditable;

import java.time.LocalDateTime;

public interface UpdateableEntity<I> {

    I getUpdatedBy();

    void setUpdatedBy(I user);

    LocalDateTime getUpdatedOn();

    void setUpdatedOn(LocalDateTime dateTime);
}

