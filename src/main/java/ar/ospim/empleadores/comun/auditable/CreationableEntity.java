package ar.ospim.empleadores.comun.auditable;

import java.time.LocalDateTime;

public interface CreationableEntity<I> {

    I getCreatedBy();

    void setCreatedBy(I user);

    LocalDateTime getCreatedOn();

    void setCreatedOn(LocalDateTime dateTime);
}
